package medi_labo.patient_information;

import medi_labo.patient_information.config.IdCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatInformationService {

    @Autowired
    private PatInformationRepository patInformationRepository;

    @Autowired
    private IdCounterService idCounterService;

    public List<PatInformation> getAllPatInformation() {
        return patInformationRepository.findAll();
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

    public BirthDayGenderDTO getBirthDayGenderByPatId(String patId) {
        BirthDayGenderDTO birthDayGenderDTO = new BirthDayGenderDTO();
        PatInformation patInformation = getPatInformationByPatId(patId);
        birthDayGenderDTO.setBirthDay(patInformation.getBirthDay());
        birthDayGenderDTO.setGender(patInformation.getGender());
        return birthDayGenderDTO;
    }

    public PatInformation addPatInformation(PatInformation patInformation) {
        String patId = Integer.toString(idCounterService.generateCounter("patId"));
        patInformation.setPatId(patId);
        return patInformationRepository.save(patInformation);
    }

    public PatInformation updatePatInformation(String patId, PatInformation patInformation) {
        PatInformation patInformationToUpdate = getPatInformationByPatId(patId);
        patInformationToUpdate.setLastName(patInformation.getLastName());
        patInformationToUpdate.setFirstName(patInformation.getFirstName());
        patInformationToUpdate.setBirthDay(patInformation.getBirthDay());
        patInformationToUpdate.setGender(patInformation.getGender());
        patInformationToUpdate.setAddress(patInformation.getAddress());
        patInformationToUpdate.setPhone(patInformation.getPhone());
        return patInformationRepository.save(patInformationToUpdate);
    }

    public void deletePatInformation(String patId) {
        PatInformation patInformation = getPatInformationByPatId(patId);
        patInformationRepository.deleteById(patInformation.getPatId());
    }

    public Integer calculateAgePatient(String birthday) {
        LocalDate birthdayLocalDate = LocalDate.parse(birthday);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthdayLocalDate, currentDate);
        return age.getYears();
    }
}
