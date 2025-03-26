package medi_labo.patient_assessment;

import medi_labo.patient_history.PatHistory;
import medi_labo.patient_information.PatInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

        ResponseEntity<List<PatHistory>> patientNoteListEntity = patHistoryClient.getPatientHistoryByPatId(patId);
        ResponseEntity<PatInformation> patInformationResponseEntity = patInformationClient.getPatientInformationByPatId(patId);
        List<PatHistory> patientNoteList = patientNoteListEntity.getBody();
        PatInformation patInformation = patInformationResponseEntity.getBody();
        List<String> patientNoteList2 = new ArrayList<>();
        patientNoteList2.add(patientNoteList.get(0).getNote());
        PatAssessment patAssessment = patAssessmentService.generatePatAssessment(patId,patientNoteList2,patInformation.getBirthDay(), patInformation.getGender());

        return ResponseEntity.ok(patAssessment);
    }
}
