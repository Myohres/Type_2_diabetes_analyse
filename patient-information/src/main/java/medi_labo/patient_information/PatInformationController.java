package medi_labo.patient_information;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pat-information")

@Validated
public class PatInformationController {

    private final String pathController = "/information";
    private static final Logger log = LoggerFactory.getLogger(PatInformationController.class);

    @Autowired
    private PatInformationService patInformationService;

    @GetMapping("/")
    public ResponseEntity<List<PatInformation>> getPatientInformation() {
        log.info("GET " + pathController + "/");
        try {
            return ResponseEntity.ok(
                    patInformationService.getAllPatInformation());
        } catch (NoSuchElementException e) {
            log.error("getAllPatInformation error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patId/{patId}")
    public ResponseEntity<PatInformation> getPatientInformationByPatId(@PathVariable(name="patId") String patId){
        log.info("GET " + pathController + "/patId/{}", patId);
        try {
            return ResponseEntity.ok(
                    patInformationService.getPatInformationByPatId(patId));
        } catch (NoSuchElementException e) {
            log.error("getPatInformationByPatId error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/information")
    public ResponseEntity<List<PatInformation>> getPatientByAllInformation(
            @RequestParam(required = false) String patId,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @Nullable
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = false)
            LocalDate birthDay,
            @Nullable
            @Pattern(
                    regexp = "^$|M|F",
                    message = "gender doit être 'M' ou 'F'"
            )
            @RequestParam(required = false)
            String gender,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone
    ) {
        log.info("GET {}/information - patId: {}, lastName: {}, firstName: {}, birthDay: {}, gender: {}, address: {}, phone: {}",
                pathController, patId, lastName, firstName, birthDay, gender, address, phone);
        try {
            return ResponseEntity.ok(
                    patInformationService.getPatInformationByAllInformation(patId,
                            lastName,firstName,birthDay,gender,address,phone)
            );
        } catch (NoSuchElementException e) {
            log.error("getPatientByAllInformation error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DateTimeParseException e) {
            log.error("Erreur de format de date : {}", e.getMessage());
            return ResponseEntity.badRequest().body(null); // Retourne une réponse badRequest si la date est invalide
        }
    }

    @GetMapping("/{patId}/birthDayGender")
    public ResponseEntity<BirthDayGenderDTO> getBirthDayGenderByPatId(@PathVariable String patId){
        log.info("GET {}/BirthDayGender", patId);
        try {
            return ResponseEntity.ok(patInformationService.getBirthDayGenderByPatId(patId));
        } catch (NoSuchElementException e) {
            log.error("getBirthDayGenderByPatId error {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add/")
    public ResponseEntity<PatInformation> createPatient(@Valid @RequestBody PatInformation patInformation) {
        log.info("POST " + pathController + "/add{}{}{}{}{}{}",
                patInformation.getLastName(),
                patInformation.getFirstName(),
                patInformation.getBirthDay(),
                patInformation.getGender(),
                patInformation.getAddress(),
                patInformation.getPhone());
        try {
            return ResponseEntity.ok(
                    patInformationService.addPatInformation(patInformation)
            );} catch (Exception e){
            log.error("createPatInformation error : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{patId}")
    public ResponseEntity<PatInformation> updatePatient(
            @PathVariable String patId ,
            @Valid @RequestBody PatInformation patInformation) {
        log.info("PUT " + pathController + "/update/{}{}{}{}{}{}{}",
                patId, patInformation.getLastName(),
                patInformation.getFirstName(),
                patInformation.getBirthDay(),
                patInformation.getGender(),
                patInformation.getAddress(),
                patInformation.getPhone());
        try {
            return ResponseEntity.ok(
                    patInformationService.updatePatInformation(patId, patInformation)
            );
        } catch (NoSuchElementException e){
            log.error("updatePatInformation error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{patId}")
    public ResponseEntity<Void> deletePatient(@PathVariable(name = "patId") String patId) {
        log.info("DELETE " + pathController + "/delete/{}", patId);
        try {
            patInformationService.deletePatInformation(patId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e){
            log.error("deletePatInformation error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
