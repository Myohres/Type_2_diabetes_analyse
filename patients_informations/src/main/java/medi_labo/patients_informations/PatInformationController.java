package medi_labo.patients_informations;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:5173/")
@Validated
public class PatInformationController {

    private final String pathController = "/patient";
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
            log.error("getAllPatInformation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/")
    public ResponseEntity<PatInformation> getPatientInformationById(String id) {
        log.info("GET " + pathController + "/id/" +id);
        try {
            return ResponseEntity.ok(
                    patInformationService.getPatInformationById(id));
        } catch (NoSuchElementException e) {
            log.error("getPatientInformationById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patId/")
    public ResponseEntity<PatInformation> getPatientInformationByPatId(String patId){
        log.info("GET " + pathController + "/patId/" +patId);
        try {
            return ResponseEntity.ok(
                    patInformationService.getPatInformationByPatId(patId));
        } catch (NoSuchElementException e) {
            log.error("getPatInformationByPatId error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lastName/")
    public ResponseEntity<PatInformation> getPatientInformationByLastName(String lastName) {
        log.info("GET " + pathController + "/lastName/" +lastName);
        try {
            return ResponseEntity.ok(
                    patInformationService.getPatInformationByLastName(lastName));
        } catch (NoSuchElementException e) {
            log.error("getPatientInformationByLastName error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/information/")
    public ResponseEntity<List<PatInformation>> getPatientByAllInformation(
            @RequestParam(required = false) String patId,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @Nullable

            @Pattern(regexp = "^$|\\d{4}-\\d{2}-\\d{2}", message = "birthDay doit être au format YYYY-MM-DD")
            @RequestParam(required = false)
            String birthDay,

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
            );} catch (NoSuchElementException e) {

            log.error("getPatientByAllInformation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/age/")
    public ResponseEntity<Integer> getPatientAge(
            @RequestParam
            @Pattern(
                    regexp = "\\d{4}-\\d{2}-\\d{2}",
                    message = "La date de naissance doit être au format YYYY/MM/DD")
            String birthDay) {
        log.info("GET " + pathController + "/age/" +birthDay);
        try {
            return ResponseEntity.ok(
                    patInformationService.calculateAgePatient(birthDay)
            );
        } catch (Exception e) {
            log.error("getPatientAge error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add/")
    public ResponseEntity<PatInformation> createPatient(@Valid @RequestBody PatInformation patInformation) {
        log.info("POST " + pathController + "/add"
                +patInformation.getLastName()
                +patInformation.getFirstName()
                +patInformation.getBirthDay()
                +patInformation.getGender()
                +patInformation.getAddress()
                +patInformation.getPhone()
        );
        try {
            return ResponseEntity.ok(
                    patInformationService.addPatInformation(patInformation)
            );} catch (Exception e){
            log.error("createPatInformation error : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatInformation> updatePatient(
            @PathVariable String id ,
            @Valid @RequestBody PatInformation patInformation) {
        log.info("PUT " + pathController + "/update/" +id
                +patInformation.getLastName()
                +patInformation.getFirstName()
                +patInformation.getBirthDay()
                +patInformation.getGender()
                +patInformation.getAddress()
                +patInformation.getPhone());
        try {
            return ResponseEntity.ok(
                    patInformationService.updatePatInformation(id, patInformation)
            );
        } catch (NoSuchElementException e){
            log.error("updatePatInformation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/id/")
    public ResponseEntity<Void> deletePatient(@RequestParam String id) {
        log.info("DELETE " + pathController + "/delete/" +id);
        try {
            patInformationService.deletePatInformation(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e){
            log.error("deletePatInformation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
