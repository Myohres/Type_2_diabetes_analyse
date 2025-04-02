package medi_labo.patient_information;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatInformationRepository extends MongoRepository<PatInformation, String> {

    Optional<PatInformation> findByPatId(String patId);
    List<PatInformation> findByPatIdOrLastNameOrFirstNameOrBirthDayOrGenderOrAddressOrPhone(
            String patId, String lastName, String firstName, LocalDate birthDay, String gender, String address, String phone);
}
