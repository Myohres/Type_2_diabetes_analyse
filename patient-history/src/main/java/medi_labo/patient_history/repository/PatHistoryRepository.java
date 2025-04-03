package medi_labo.patient_history.repository;

import medi_labo.patient_history.model.entity.PatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatHistoryRepository extends MongoRepository<PatHistory, String> {

    List<PatHistory> findByPatient(String patient);
    List<PatHistory> findByPatId(String patId);
}
