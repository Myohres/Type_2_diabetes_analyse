package medi_labo.patient_historique;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatHistoriqueRepository extends MongoRepository<PatHistorique, String> {

    List<PatHistorique> findByPatient(String patient);
    List<PatHistorique> findByPatId(String patId);
}
