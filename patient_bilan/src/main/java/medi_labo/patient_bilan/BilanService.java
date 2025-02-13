package medi_labo.patient_bilan;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class BilanService {

    public Bilan generateBilan(String PatId, List<String> patientNoteList, String birthday, String gender) {
        Bilan bilan = new Bilan();
        Integer triggerWordNumber = getTriggerWordNumber(patientNoteList);
        Integer age = calculateAgePatient(birthday);
        String riskLevel = evaluationRiskLevel(triggerWordNumber, age, gender);
        bilan.setPatId(PatId);
        bilan.setRiskLevel(riskLevel);
        return bilan;
    }

    public Integer calculateAgePatient(String birthday) {
        LocalDate birthdayLocalDate = LocalDate.parse(birthday);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthdayLocalDate, currentDate);
        return age.getYears();
    }

    public Integer getTriggerWordNumber(List<String> patientNoteList) {
        Integer triggerWordNumber = 0;
        try {
            InputStream inputStream = new ClassPathResource("triggerWordsList.txt").getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if(patientNoteList.contains(line)) {
                        triggerWordNumber++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
