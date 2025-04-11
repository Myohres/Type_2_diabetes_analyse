package medi_labo.patient_assessment.service;

import medi_labo.patient_assessment.model.PatAssessment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


public class PatAssessmentService {

    private static final Logger log = LoggerFactory.getLogger(PatAssessmentService.class);

    public PatAssessment generatePatAssessment(String PatId, List<String> patientNoteList, LocalDate birthday, String gender) {
        PatAssessment patAssessment = new PatAssessment();
        Integer triggerWordNumber = getTriggerWordNumber(patientNoteList);
        Integer age = calculateAgePatient(birthday);
        System.out.println(triggerWordNumber);
        String riskLevel = evaluationRiskLevel(triggerWordNumber, age, gender);
        patAssessment.setPatId(PatId);
        patAssessment.setRiskLevel(riskLevel);
        return patAssessment;
    }

    public Integer calculateAgePatient(LocalDate birthday) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthday, currentDate);

        Integer ageInYears = age.getYears();

        return ageInYears;
    }

    public Integer getTriggerWordNumber(List<String> patientNoteList) {
            int triggerWordNumber = 0;

            try (InputStream inputStream = new ClassPathResource("triggerWordsList.txt").getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                List<String> triggerWords = reader.lines().toList();

                triggerWordNumber = (int) triggerWords.stream()
                        .filter(word -> {
                            boolean found = patientNoteList.stream().anyMatch(note -> note.contains(word));
                            if (found) System.out.println("Trigger word trouvé : " + word);
                            return found;
                        })
                        .count();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        System.out.println(triggerWordNumber);
            return triggerWordNumber;
        }


    public String evaluationRiskLevel(Integer triggerWordNumber, Integer age, String gender) {
        System.out.println(triggerWordNumber);
        if (triggerWordNumber == 0) {
            return "None";
        }
        if (triggerWordNumber >= 2 && triggerWordNumber <= 5 && age > 30) {
            return "Borderline";
        }
        if (gender.equals("M") && triggerWordNumber >= 3 && age < 30) {
            return "In Danger";
        }
        if (gender.equals("F") && triggerWordNumber >= 4 && age < 30) {
            return "In Danger";
        }
        if ( triggerWordNumber >= 6 && triggerWordNumber <= 7 && age > 30) {
            return "In Danger";
        }
        if (gender.equals("M") && triggerWordNumber >= 5 && age < 30) {
            return "Early onset";
        }
        if (gender.equals("F") && triggerWordNumber >= 7 && age < 30) {
            return "Early onset";
        }
        if ( triggerWordNumber >= 8 && age > 30) {
            return "Early onset";
        }
        else {
            return "No situation";
        }
    }

}
