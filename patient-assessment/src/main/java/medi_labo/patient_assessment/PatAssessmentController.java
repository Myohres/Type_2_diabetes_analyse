package medi_labo.patient_assessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/assessment")
@CrossOrigin(origins = "http://localhost:5173/")
public class PatAssessmentController {

    private static final Logger log = LoggerFactory.getLogger(PatAssessmentController.class);


    private final PatAssessmentService patAssessmentService = new PatAssessmentService();

    @PostMapping("")
    public ResponseEntity<PatAssessment> getPatAssessment(@RequestBody RequestPatAssessment requestPatAssessment) {
        log.info("POST /assessment/");
        try {
           return ResponseEntity.ok(patAssessmentService.generatePatAssessment(
                   requestPatAssessment.getPatId(),
                   requestPatAssessment.getPatientNoteList(),
                   requestPatAssessment.getBirthDay(),
                   requestPatAssessment.getGender()));
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }

    }
}
