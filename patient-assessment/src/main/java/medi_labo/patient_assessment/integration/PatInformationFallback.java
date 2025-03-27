package medi_labo.patient_assessment.integration;

import medi_labo.patient_assessment.dto.BirthDayGenderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PatInformationFallback implements PatInformationClient{

    @Override
    public ResponseEntity<BirthDayGenderDTO> getBirthDayGenderByPatId(String patId) {
        return ResponseEntity.notFound().build();
    }


}
