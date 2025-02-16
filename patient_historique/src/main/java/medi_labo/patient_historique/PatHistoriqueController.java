package medi_labo.patient_historique;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/historique")
@Controller
public class PatHistoriqueController {

    private static final Logger log = LoggerFactory.getLogger(PatHistoriqueController.class);

    @Autowired
    private PatHistoriqueService patHistoriqueService;

    @GetMapping("")
    public ResponseEntity<List<PatHistorique>> findAll() {
        log.info("Find all pat historiques");
        try {
            return ResponseEntity.ok(patHistoriqueService.getAllPatHistorique());
        } catch (NoSuchElementException e) {
            log.info("No pat historiques found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id")
    public ResponseEntity<PatHistorique> getPatHistoriqueById(@RequestParam("id") String id) {
        log.info("GET/historique/id" +id );
        try {
            return ResponseEntity.ok(patHistoriqueService.getPatHistoriqueById(id));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patId")
    public ResponseEntity<List<PatHistorique>> getPatHistoriqueByPatId(@RequestParam("patId") String patId) {
        log.info("GET/historique/patId" +patId );
        try {
            return ResponseEntity.ok(patHistoriqueService.getPatHistoriquesByPatId(patId));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient")
    public ResponseEntity<List<PatHistorique>> getPatHistoriqueByPatient(@RequestParam("patient") String patient) {
        log.info("GET/historique/patient" +patient );
        try {
            return ResponseEntity.ok(patHistoriqueService.getPatHistoriquesByPatientName(patient));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<PatHistorique> addPatHistorique(@RequestBody PatHistorique patHistorique) {
        log.info("POST/historique/add");
        try {
            return ResponseEntity.ok(patHistoriqueService.addPatHistorique(patHistorique));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/note")
    public ResponseEntity<PatHistorique> addNote(@RequestParam("patId") String patId,
                                                 @RequestParam("patient") String patient,
                                                 @RequestParam("note") String note) {
        log.info("POST/historique/note " );
        try {
            return ResponseEntity.ok(patHistoriqueService.addNote(patId,patient,note));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/note")
    public ResponseEntity<PatHistorique> updateNote(@RequestParam("id") String id,
                                                    @RequestParam("note") String note) {
        log.info("PUT/historique/note " +id +note );
        try {
            return ResponseEntity.ok(patHistoriqueService.updatePatHistorique(id,note));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePatHistorique(@RequestParam("id") String id) {
        log.info("DELETE/historique/delete " +id );
        try {
            patHistoriqueService.deletePatHistorique(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
