package medi_labo.patient_bilan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class BilanService {

    private static final Logger log = LoggerFactory.getLogger(BilanService.class);

    public Bilan generateBilan(String PatId, List<String> patientNoteList, String birthday, String gender) {
        log.info("Generate Bilan");
        Bilan bilan = new Bilan();
        Integer triggerWordNumber = getTriggerWordNumber(patientNoteList);
        Integer age = calculateAgePatient(birthday);
        String riskLevel = evaluationRiskLevel(triggerWordNumber, age, gender);
        bilan.setPatId(PatId);
        bilan.setRiskLevel(riskLevel);
        log.info("Risk Level: " + riskLevel);
        return bilan;
    }

    public Integer calculateAgePatient(String birthday) {
        log.info("Calculate age patient");
        LocalDate birthdayLocalDate = LocalDate.parse(birthday);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthdayLocalDate, currentDate);

        Integer ageInYears = age.getYears();
        log.info(String.valueOf(ageInYears));
        return age.getYears();
    }

    public Integer getTriggerWordNumber(List<String> patientNoteList) {
            int triggerWordNumber = 0;

            try (InputStream inputStream = new ClassPathResource("triggerWordsList.txt").getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                List<String> triggerWords = reader.lines().toList();

                triggerWordNumber = (int) triggerWords.stream()
                        .filter(word -> patientNoteList.stream().anyMatch(note -> note.contains(word)))
                        .count();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            log.info("Trigger Word Number: " + triggerWordNumber);
            return triggerWordNumber;
        }


    public String evaluationRiskLevel(Integer triggerWordNumber, Integer age, String gender) {
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
