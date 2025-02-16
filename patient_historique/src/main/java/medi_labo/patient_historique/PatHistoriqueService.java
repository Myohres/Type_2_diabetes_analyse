package medi_labo.patient_historique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatHistoriqueService {

    @Autowired
    private PatHistoriqueRepository patHistoriqueRepository;

    public List<PatHistorique> getAllPatHistorique() {
        List<PatHistorique> patHistorique = patHistoriqueRepository.findAll();
        if (patHistorique.isEmpty()) {
            throw new NoSuchElementException("No patHistorique found");
        } else {
            return patHistorique;
        }
    }

    public PatHistorique getPatHistoriqueById(String id) {
       Optional<PatHistorique> patHistorique = patHistoriqueRepository.findById(id);
       if (patHistorique.isPresent()) {
           return patHistorique.get();
       } else {
           throw new NoSuchElementException("No patHistorique with id : " +id +" found");
       }
    }

    public List<PatHistorique> getPatHistoriquesByPatientName(String patientName) {
        List<PatHistorique> patHistoriqueList = patHistoriqueRepository.findByPatient(patientName);
        if (patHistoriqueList.isEmpty()) {
            throw new NoSuchElementException("PatHistorique with name : " +patientName + " not found");
        } else {
            return patHistoriqueList;
        }
    }

    public List<PatHistorique> getPatHistoriquesByPatId(String patId) {
        List<PatHistorique> patHistoriqueList = patHistoriqueRepository.findByPatId(patId);
        if (patHistoriqueList.isEmpty()) {
            throw new NoSuchElementException("PatHistorique with id : " +patId + " not found");
        } else {
            return patHistoriqueList;
        }
    }

    public PatHistorique addPatHistorique(PatHistorique patHistorique) {
        return patHistoriqueRepository.save(patHistorique);
    }

    public PatHistorique addNote(String patId,String patient, String note) {
        PatHistorique patHistorique = new PatHistorique();
        patHistorique.setPatId(patId);
        patHistorique.setPatient(patient);
        patHistorique.setNote(note);
        return patHistoriqueRepository.save(patHistorique);
    }

    public PatHistorique updatePatHistorique(String id, String note) {
        PatHistorique patHistorique = getPatHistoriqueById(id);
        patHistorique.setNote(note);
        return patHistoriqueRepository.save(patHistorique);
    }

    public void deletePatHistorique(String id) {
        patHistoriqueRepository.deleteById(id);
    }
}
