package medi_labo.patient_bilan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessment/")
@CrossOrigin(origins = "http://localhost:5173/")
public class BilanController {

    private static final Logger log = LoggerFactory.getLogger(BilanController.class);


    private final BilanService bilanService = new BilanService();

    @GetMapping("")
    public ResponseEntity<Bilan> getBilan(
            @RequestParam String patId,
            @RequestBody List<String> patientNoteList,
            @RequestParam String birthDay,
            @RequestParam String gender) {
        log.info("GET /assessment/");
        try {
           return ResponseEntity.ok(bilanService.generateBilan(patId, patientNoteList, birthDay, gender));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
