package medi_labo.patient_assessment.integration;

import medi_labo.patient_assessment.config.FeignConfig;
import medi_labo.patient_assessment.dto.BirthDayGenderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-information", url = "http://patient-information:8080",configuration = FeignConfig.class)
public interface PatInformationClient {

    @GetMapping("/pat-information/{patId}/birthDayGender")
    ResponseEntity<BirthDayGenderDTO> getBirthDayGenderByPatId (@PathVariable("patId") String patId);

}
