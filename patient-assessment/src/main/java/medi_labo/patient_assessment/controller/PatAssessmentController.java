package medi_labo.patient_assessment.controller;

import feign.FeignException;
import medi_labo.patient_assessment.dto.RequestPatAssessment;
import medi_labo.patient_assessment.exception.CustomExceptions;
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

        BirthDayGenderDTO birthDayGender;
        List<String> noteListHistories;

        try {
            ResponseEntity<BirthDayGenderDTO> patInformationResponseEntity = patInformationClient.getBirthDayGenderByPatId(patId);
            birthDayGender = patInformationResponseEntity.getBody();
        } catch (CustomExceptions.ResourceNotFoundException e) {
            log.error("Patient introuvable (404) : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Patient non trouvé pour l'ID " + patId);
        } catch (FeignException e) {
            log.error("Erreur Feign lors de la récupération des infos patient : {} - {}", e.status(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Erreur lors de la communication avec le service pat-information");
        } catch (Exception e) {
            log.error("Erreur interne : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur");
        }

        try {
            ResponseEntity<List<String>> patientNoteListEntity = patHistoryClient.getNoteListHistoriesByPatId(patId);
            noteListHistories = patientNoteListEntity.getBody();
        } catch (CustomExceptions.ResourceNotFoundException e) {
            log.error("Historique du patient introuvable (404) : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun historique trouvé pour le patient ID " + patId);
        } catch (FeignException e) {
            log.error("Erreur Feign lors de la récupération des notes patient : Status {} - {}",e.status(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Erreur lors de la communication avec le service pat-history");
        } catch (Exception e) {
            log.error("Erreur interne : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur");
        }

        PatAssessment patAssessment = patAssessmentService.generatePatAssessment(
                patId, noteListHistories, birthDayGender.getBirthDay(), birthDayGender.getGender()
        );

        return ResponseEntity.ok(patAssessment);
    }
}