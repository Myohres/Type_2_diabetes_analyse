package medi_labo.patients_informations;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatInformationRepository extends MongoRepository<PatInformation, String> {

    List<PatInformation> findByLastNameOrFirstName(String lastName, String firstName);

    Optional<PatInformation> findByPatId(String patId);
    Optional<PatInformation> findByLastName(String lastName);
    List<PatInformation> findByPatIdOrLastNameOrFirstNameOrBirthDayOrGenderOrAddressOrPhone(
           String patId, String lastName, String firstName, String birthDay, String gender, String address, String phone);
}
