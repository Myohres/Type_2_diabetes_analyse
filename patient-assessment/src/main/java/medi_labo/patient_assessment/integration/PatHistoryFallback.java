package medi_labo.patient_assessment.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatHistoryFallback implements PatHistoryClient {

    @Override
    public ResponseEntity<List<String>> getNoteListHistoriesByPatId(String id) {
        List<String> fallBackList = List.of("Donnée indisponible, Patient-History module indisponible ");
        return ResponseEntity.status(503).body(fallBackList);
    }
}
