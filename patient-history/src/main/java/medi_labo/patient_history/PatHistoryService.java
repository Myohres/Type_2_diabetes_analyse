package medi_labo.patient_history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatHistoryService {

    @Autowired
    private PatHistoryRepository patHistoryRepository;

    public List<PatHistory> getAllPatHistory() {
        List<PatHistory> patHistory = patHistoryRepository.findAll();
        if (patHistory.isEmpty()) {
            throw new NoSuchElementException("No patHistory found");
        } else {
            return patHistory;
        }
    }

    public PatHistory getPatHistoryById(String id) {
       Optional<PatHistory> patHistory = patHistoryRepository.findById(id);
       if (patHistory.isPresent()) {
           return patHistory.get();
       } else {
           throw new NoSuchElementException("No patHistory with id : " +id +" found");
       }
    }

    public List<PatHistory> getPatHistoryByPatientName(String patientName) {
        List<PatHistory> patHistoryList = patHistoryRepository.findByPatient(patientName);
        if (patHistoryList.isEmpty()) {
            throw new NoSuchElementException("PatHistory with name : " +patientName + " not found");
        } else {
            return patHistoryList;
        }
    }

    public List<PatHistory> getPatHistoryByPatId(String patId) {
        List<PatHistory> patHistoryList = patHistoryRepository.findByPatId(patId);
        if (patHistoryList.isEmpty()) {
            throw new NoSuchElementException("PatHistory with id : " +patId + " not found");
        } else {
            return patHistoryList;
        }
    }

    public PatHistory addPatHistory(PatHistory patHistory) {
        return patHistoryRepository.save(patHistory);
    }

    public PatHistory addNote(String patId, String patient, String note) {
        PatHistory patHistory = new PatHistory();
        patHistory.setPatId(patId);
        patHistory.setPatient(patient);
        patHistory.setNote(note);
        return patHistoryRepository.save(patHistory);
    }

    public PatHistory updatePatHistory(String id, String note) {
        PatHistory patHistory = getPatHistoryById(id);
        patHistory.setNote(note);
        return patHistoryRepository.save(patHistory);
    }

    public void deletePatHistory(String id) {
        patHistoryRepository.deleteById(id);
    }
}
