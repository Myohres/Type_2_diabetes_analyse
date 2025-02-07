package medi_labo.patients_informations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patient")
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
            String lastName,
            String firstName,
            String birthDay,
            String gender,
            String address,
            String phone
    ) {
        log.info("GET " + pathController + "/information/" +lastName+firstName+birthDay+gender+address+phone);
        try {
            return ResponseEntity.ok(
                    patInformationService.getPatInformationByAllInformation(
                            lastName,firstName,birthDay,gender,address,phone)
            );} catch (NoSuchElementException e) {
            log.error("getPatientByAllInformation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/age/")
    public ResponseEntity<Integer> getPatientAge(String birthDay) {
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

    @PostMapping("/add")
    public ResponseEntity<PatInformation> createPatient(@RequestBody PatInformation patInformation) {
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

    @PutMapping("/update/id")
    public ResponseEntity<PatInformation> updatePatient(String id , @RequestBody PatInformation patInformation) {
        log.info("PUT " + pathController + "/update/id" +id
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

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePatient(String id) {
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
