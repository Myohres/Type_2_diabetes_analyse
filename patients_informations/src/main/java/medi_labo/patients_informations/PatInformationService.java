package medi_labo.patients_informations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatInformationService {

    @Autowired
    private PatInformationRepository patInformationRepository;

    public List<PatInformation> getAllPatInformation() {
        return patInformationRepository.findAll();
    }

    public PatInformation getPatInformationById(String id) {
        Optional<PatInformation> patInformation = patInformationRepository.findById(id);
        if (patInformation.isPresent()){
            return patInformation.get();
        } else {
            throw new NoSuchElementException("Patient not found");
        }
    }

    public PatInformation getPatInformationByPatId(String patId) {
        Optional<PatInformation> patInformation = patInformationRepository.findByPatId(patId);
        if (patInformation.isPresent()) {
            return patInformation.get();
        } else {
            throw new NoSuchElementException("Patient not found");
        }
    }

    public List<PatInformation> getPatInformationByAllInformation(
          String patId, String lastName, String firstName, String birthday, String gender, String address, String phone
    ) {
        List<PatInformation> patInformationList = patInformationRepository.findByPatIdOrLastNameOrFirstNameOrBirthDayOrGenderOrAddressOrPhone(
               patId, lastName, firstName, birthday, gender, address, phone);
        if (patInformationList.isEmpty()){
            throw new NoSuchElementException("Patient not found");
        } else {
            return patInformationList;
        }

    }
    public PatInformation getPatInformationByLastName(String lastName) {
        Optional<PatInformation> patInformation = patInformationRepository.findByLastName(lastName);
        if (patInformation.isPresent()){
            return patInformation.get();
        } else {
            throw new NoSuchElementException("Patient not found");
        }
    }

    public List<PatInformation> getPatInformationByName(String lastName, String firstName) {
        List<PatInformation> patInformationList =
                patInformationRepository.findByLastNameOrFirstName(lastName, firstName);
        if (patInformationList.isEmpty()){
            throw new NoSuchElementException("Patient not found");
        } else {
            return patInformationList;
        }
    }

    public PatInformation addPatInformation(PatInformation patInformation) {
        return patInformationRepository.save(patInformation);
    }

    public PatInformation updatePatInformation(String id, PatInformation patInformation) {
        PatInformation patInformationToUpdate = getPatInformationById(id);
        patInformationToUpdate.setLastName(patInformation.getLastName());
        patInformationToUpdate.setFirstName(patInformation.getFirstName());
        patInformationToUpdate.setBirthDay(patInformation.getBirthDay());
        patInformationToUpdate.setGender(patInformation.getGender());
        patInformationToUpdate.setAddress(patInformation.getAddress());
        patInformationToUpdate.setPhone(patInformation.getPhone());
        return patInformationRepository.save(patInformationToUpdate);
    }

    public void deletePatInformation(String id) {
        PatInformation patInformation = getPatInformationById(id);
        patInformationRepository.deleteById(patInformation.getId());
    }

    public Integer calculateAgePatient(String birthday) {
        LocalDate birthdayLocalDate = LocalDate.parse(birthday);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthdayLocalDate, currentDate);
        return age.getYears();
    }
}
