package medi_labo.patients_informations.config;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdCounterRepo extends MongoRepository<IdCounter, String> {
}
