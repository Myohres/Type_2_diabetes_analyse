package medi_labo.patient_bilan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/bilan")
@CrossOrigin(origins = "http://localhost:5173/")
public class BilanController {

    private static final Logger log = LoggerFactory.getLogger(BilanController.class);


    private final BilanService bilanService = new BilanService();

    @PostMapping("")
    public ResponseEntity<Bilan> getBilan(@RequestBody RequestBilan requestBilan) {
        log.info("POST /assessment/");
        try {
           return ResponseEntity.ok(bilanService.generateBilan(
                   requestBilan.getPatId(),
                   requestBilan.getPatientNoteList(),
                   requestBilan.getBirthDay(),
                   requestBilan.getGender()));
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }

    }
}
