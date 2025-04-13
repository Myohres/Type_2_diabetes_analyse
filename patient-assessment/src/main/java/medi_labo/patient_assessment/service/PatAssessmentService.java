package medi_labo.patient_assessment.service;

import medi_labo.patient_assessment.model.Entity.PatAssessment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;


public class PatAssessmentService {

    public PatAssessment generatePatAssessment(String PatId, List<String> patientNoteList, LocalDate birthday, String gender) {
        PatAssessment patAssessment = new PatAssessment();
        Integer triggerWordNumber = getTriggerWordNumber(patientNoteList);
        Integer age = calculateAgePatient(birthday);
        String riskLevel = evaluationRiskLevel(triggerWordNumber, age, gender);
        patAssessment.setPatId(PatId);
        patAssessment.setRiskLevel(riskLevel);
        return patAssessment;
    }

    public Integer calculateAgePatient(LocalDate birthday) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthday, currentDate);
        return age.getYears();
    }

    public Integer getTriggerWordNumber(List<String> patientNoteList) {
            int triggerWordNumber = 0;

            try (InputStream inputStream = new ClassPathResource("triggerWordsList.txt").getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                List<String> triggerWords = reader.lines().toList();

                triggerWordNumber = (int) triggerWords.stream()
                        .filter(word -> {
                            Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
                            boolean found = patientNoteList.stream()
                                    .anyMatch(note -> pattern.matcher(note).find());
                            return found;
                        })
                        .count();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return triggerWordNumber;
        }


    public String evaluationRiskLevel(Integer triggerWordNumber, Integer age, String gender) {
        if (age > 30) {
            if (triggerWordNumber < 2) {
                return "None";
            }
            if (triggerWordNumber < 6) {
                return "Bordeline";
            }
            if (triggerWordNumber < 8) {
                return "In danger";
            } else {
                return "Early onset";
            }
        } else {
            if (gender.equals("M")) {
                if (triggerWordNumber < 3) {
                    return "None";
                }
                if (triggerWordNumber < 5) {
                    return "In danger";
                } else {
                    return "Early onset";
                }
            }
            if (gender.equals("F")) {
                if (triggerWordNumber < 4) {
                    return "None";
                }
                if (triggerWordNumber < 7) {
                    return "In danger";
                } else {
                    return "early onset";
                }
            } else {
                return "Ni homme ni femme, impossible d'appliquer le protocole, pour le coup bon courage...";
            }
        }
    }
}
