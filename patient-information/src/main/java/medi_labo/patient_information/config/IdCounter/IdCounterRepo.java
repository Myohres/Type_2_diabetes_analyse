package medi_labo.patient_information.config.IdCounter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdCounterRepo extends MongoRepository<IdCounter, String> {
}
