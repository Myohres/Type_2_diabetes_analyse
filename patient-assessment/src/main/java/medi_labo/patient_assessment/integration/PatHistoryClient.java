package medi_labo.patient_assessment.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient-history", url = "localhost:8081", fallback = PatHistoryFallback.class)
public interface PatHistoryClient {

    @GetMapping("history/noteListHistories/{patId}")
    ResponseEntity<List<String>> getNoteListHistoriesByPatId(@PathVariable("patId") String patId);
}
