package medi_labo.patient_history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/pat-history")
@Controller
@CrossOrigin(origins = "http://localhost:5173/")
public class PatHistoryController {

    private static final Logger log = LoggerFactory.getLogger(PatHistoryController.class);

    @Autowired
    private PatHistoryService patHistoryService;

    @GetMapping("")
    public ResponseEntity<List<PatHistory>> findAll() {
        log.info("Find all PatHistory");
        try {
            return ResponseEntity.ok(patHistoryService.getAllPatHistory());
        } catch (NoSuchElementException e) {
            log.info("No PatHistory found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id")
    public ResponseEntity<PatHistory> getPatHistoryById(@RequestParam("id") String id) {
        log.info("GET/History/id{}", id);
        try {
            return ResponseEntity.ok(patHistoryService.getPatHistoryById(id));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patId/{patId}")
    public ResponseEntity<List<PatHistory>> getPatHistoryByPatId(@PathVariable("patId") String patId) {
        log.info("GET/History/patId{}", patId);
        try {
            return ResponseEntity.ok(patHistoryService.getPatHistoryByPatId(patId));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient")
    public ResponseEntity<List<PatHistory>> getPatHistoryByPatient(@RequestParam("patient") String patient) {
        log.info("GET/History/patient{}", patient);
        try {
            return ResponseEntity.ok(patHistoryService.getPatHistoryByPatientName(patient));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/noteListHistories/{patId}")
    public ResponseEntity<List<String>> getNoteListHistoryByPatId(@PathVariable("patId") String patId) {
        log.info("GET/History/noteListHistory/{}", patId);
        try {
            List<PatHistory> patHistories = patHistoryService.getPatHistoryByPatId(patId);
            List<String> noteList =
                    patHistories
                            .stream()
                            .map(PatHistory::getNote).toList();
            return ResponseEntity.ok(noteList);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patHistories/{patId}")
    public ResponseEntity<PatHistoriesDTO> getPatHistoriesByPatId(@PathVariable("patId") String patId) {
        log.info("GET/History/patHistories/{}", patId);
        try {
            return ResponseEntity.ok(patHistoryService.getPatHistoriesDTOByPatId(patId));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<PatHistory> addPatHistory(@RequestBody PatHistory patHistory) {
        log.info("POST/History/add");
        try {
            return ResponseEntity.ok(patHistoryService.addPatHistory(patHistory));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/note")
    public ResponseEntity<PatHistory> addNote(@RequestBody PatHistory patHistory) {
        log.info("POST/History/note {}{}{}", patHistory.getPatId(), patHistory.getPatient(), patHistory.getNote());

        try {
            return ResponseEntity.ok(patHistoryService.addNote(patHistory.getPatId(),patHistory.getPatient(),patHistory.getNote()));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/note")
    public ResponseEntity<PatHistory> updateNote(@RequestParam("id") String id,
                                                 @RequestParam("note") String note) {
        log.info("PUT/History/note {}{}", id, note);
        try {
            return ResponseEntity.ok(patHistoryService.updatePatHistory(id,note));
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePatHistory(@RequestParam("id") String id) {
        log.info("DELETE/History/delete {}", id);
        try {
            patHistoryService.deletePatHistory(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
