package medi_labo.patient_assessment.integration;

import medi_labo.patient_assessment.dto.BirthDayGenderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-information", url = "localhost:8080")
public interface PatInformationClient {

    @GetMapping("/information/BirthDayGender/{patId}")
    ResponseEntity<BirthDayGenderDTO> getBirthDayGenderByPatId (@PathVariable("patId") String patId);

}
