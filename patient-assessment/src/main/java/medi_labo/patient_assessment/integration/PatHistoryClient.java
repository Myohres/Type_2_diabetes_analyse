package medi_labo.patient_assessment.integration;

import medi_labo.patient_assessment.config.FeignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient-history", url = "http://localhost:8081", configuration = FeignConfig.class)
public interface PatHistoryClient {

    @GetMapping("/pat-history/noteListHistories/{patId}")
    ResponseEntity<List<String>> getNoteListHistoriesByPatId(@PathVariable("patId") String patId);
}
