package medi_labo.patient_assessment;

import medi_labo.patient_history.NoteListHistoriesDTO;
import medi_labo.patient_history.PatHistory;
import medi_labo.patient_information.BirthDayGenderDTO;
import medi_labo.patient_information.PatInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/assessment")
@CrossOrigin(origins = "http://localhost:5173/")
public class PatAssessmentController {

    private static final Logger log = LoggerFactory.getLogger(PatAssessmentController.class);

    @Autowired
    private PatHistoryClient patHistoryClient;

    @Autowired
    private PatInformationClient patInformationClient;

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

    @PostMapping("/patId/{patId}")
    public ResponseEntity<PatAssessment> getPatAssessmentByPatId(@PathVariable("patId") String patId) {
        log.info("POST /assessment/patId/{}",patId);

        try {
            ResponseEntity<List<String>> patientNoteListEntity = patHistoryClient.getNoteListHistoriesByPatId(patId);
            ResponseEntity<BirthDayGenderDTO> patInformationResponseEntity = patInformationClient.getBirthDayGenderByPatId(patId);
            List<String> noteListHistories = patientNoteListEntity.getBody();
            BirthDayGenderDTO birthDayGender = patInformationResponseEntity.getBody();
            PatAssessment patAssessment = patAssessmentService.generatePatAssessment(patId,noteListHistories,birthDayGender.getBirthDay(), birthDayGender.getGender());
            return ResponseEntity.ok(patAssessment);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }



    }
}
