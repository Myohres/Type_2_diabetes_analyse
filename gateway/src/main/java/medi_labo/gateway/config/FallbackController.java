package medi_labo.gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity<Map<String, String>> fallback() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Service indisponible. Veuillez réessayer plus tard.");
        response.put("status", "503");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
