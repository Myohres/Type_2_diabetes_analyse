package medi_labo.patient_history.controller;

import jakarta.validation.Valid;
import medi_labo.patient_history.model.DTO.PatHistoriesDTO;
import medi_labo.patient_history.model.DTO.PatHistoriesNoteDTO;
import medi_labo.patient_history.model.entity.PatHistory;
import medi_labo.patient_history.service.PatHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pat-history")
@RestController
@Validated
public class PatHistoryController {

    private static final Logger log = LoggerFactory.getLogger(PatHistoryController.class);

    @Autowired
    private PatHistoryService patHistoryService;

    @GetMapping("")
    public ResponseEntity<PatHistoriesDTO> findAll() {
        log.info("GET /");
            return ResponseEntity.ok(patHistoryService.getAllPatHistory());
    }

    @GetMapping("/id")
    public ResponseEntity<PatHistory> getPatHistoryById(@RequestParam("id") String id) {
        log.info("GET/History/id{}", id);
            return ResponseEntity.ok(patHistoryService.getPatHistoryById(id));
    }

    @GetMapping("/patId/{patId}")
    public ResponseEntity<PatHistoriesDTO> getPatHistoryByPatId(@PathVariable("patId") String patId) {
        log.info("GET/History/patId{}", patId);
            return ResponseEntity.ok(patHistoryService.getPatHistoryByPatId(patId));
    }

    @GetMapping("/noteListHistories/{patId}")
    public ResponseEntity<List<String>> getNoteListHistoryByPatId(@PathVariable("patId") String patId) {
        log.info("GET/History/noteListHistory/{}", patId);
            PatHistoriesDTO patHistories = patHistoryService.getPatHistoryByPatId(patId);
            List<String> noteList =
                    patHistories.getNoteListHistories()
                            .stream()
                            .map(PatHistoriesNoteDTO::getNote).toList();
            return ResponseEntity.ok(noteList);
    }

    @PostMapping("/add")
    public ResponseEntity<PatHistory> addPatHistory(@Valid @RequestBody PatHistory patHistory) {
        log.info("POST/History/add");
            return ResponseEntity.ok(patHistoryService.addPatHistory(patHistory));
    }

    @PutMapping("/note")
    public ResponseEntity<PatHistory> updateNote(@RequestParam("id") String id,
                                                 @RequestParam("note") String note) {
        log.info("PUT/History/note {}{}", id, note);
            return ResponseEntity.ok(patHistoryService.updatePatHistory(id,note));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePatHistory(@RequestParam("id") String id) {
        log.info("DELETE/History/delete {}", id);
            patHistoryService.deletePatHistory(id);
            return ResponseEntity.noContent().build();
    }
}
