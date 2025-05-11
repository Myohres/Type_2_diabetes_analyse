package medi_labo.patient_assessment.controller;

import feign.FeignException;

import medi_labo.patient_assessment.exception.CustomExceptions;
import medi_labo.patient_assessment.integration.PatHistoryClient;
import medi_labo.patient_assessment.integration.PatInformationClient;
import medi_labo.patient_assessment.model.dto.BirthDayGenderDTO;
import medi_labo.patient_assessment.model.Entity.PatAssessment;
import medi_labo.patient_assessment.service.PatAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
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

    @GetMapping("/patId/{patId}")
    public ResponseEntity<?> getPatAssessmentByPatId(@PathVariable("patId") String patId) {
        log.info("POST /assessment/patId/{}", patId);

        BirthDayGenderDTO birthDayGender;
        List<String> noteListHistories;


            ResponseEntity<BirthDayGenderDTO> patInformationResponseEntity = patInformationClient.getBirthDayGenderByPatId(patId);
            birthDayGender = patInformationResponseEntity.getBody();



        if (birthDayGender == null || birthDayGender.getBirthDay() == null || birthDayGender.getGender() == null) {
            log.error("Données patient incomplètes : birthDay ou gender est null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Les informations du patient sont incomplètes.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String formattedDate = birthDayGender.getBirthDay().format(formatter);
            log.info("Date formatée avec succès : {}", formattedDate);
        } catch (Exception e) {
            log.error("Format de date invalide pour birthDay : {}", birthDayGender.getBirthDay());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Le format de la date de naissance est invalide. Attendu : yyyy-MM-dd");
        }


            ResponseEntity<List<String>> patientNoteListEntity = patHistoryClient.getNoteListHistoriesByPatId(patId);
            noteListHistories = patientNoteListEntity.getBody();

        PatAssessment patAssessment = patAssessmentService.generatePatAssessment(
                patId, noteListHistories, birthDayGender.getBirthDay(), birthDayGender.getGender()
        );

        return ResponseEntity.ok(patAssessment);
    }
}