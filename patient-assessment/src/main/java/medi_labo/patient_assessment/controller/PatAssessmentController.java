package medi_labo.patient_assessment.controller;

import medi_labo.patient_assessment.dto.RequestPatAssessment;
import medi_labo.patient_assessment.integration.PatHistoryClient;
import medi_labo.patient_assessment.integration.PatInformationClient;
import medi_labo.patient_assessment.dto.BirthDayGenderDTO;
import medi_labo.patient_assessment.model.PatAssessment;
import medi_labo.patient_assessment.service.PatAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/pat-assessment")
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
    public ResponseEntity<?> getPatAssessmentByPatId(@PathVariable("patId") String patId) {
        log.info("POST /assessment/patId/{}", patId);
        BirthDayGenderDTO birthDayGender = new BirthDayGenderDTO();
        List<String> noteListHistories = new ArrayList<>();
        try {
            ResponseEntity<BirthDayGenderDTO> patInformationResponseEntity = patInformationClient.getBirthDayGenderByPatId(patId);
            birthDayGender = patInformationResponseEntity.getBody();
            ResponseEntity<List<String>> patientNoteListEntity = patHistoryClient.getNoteListHistoriesByPatId(patId);
            noteListHistories = patientNoteListEntity.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Erreur : {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body("Erreur : " + e.getMessage());
        } catch (Exception e) {
            log.error("Erreur : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne : " + e.getMessage());
        }
        PatAssessment patAssessment = patAssessmentService.generatePatAssessment(patId, noteListHistories, birthDayGender.getBirthDay(), birthDayGender.getGender());
        return ResponseEntity.ok(patAssessment);
    }
}