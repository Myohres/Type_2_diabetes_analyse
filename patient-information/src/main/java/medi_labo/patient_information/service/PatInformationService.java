package medi_labo.patient_information.service;

import medi_labo.patient_information.repository.PatInformationRepository;
import medi_labo.patient_information.config.IdCounter.IdCounterService;
import medi_labo.patient_information.model.dto.BirthDayGenderDTO;
import medi_labo.patient_information.model.document.PatInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            throw new NoSuchElementException("Patient not found with id: " + patId);
        }
    }

    public List<PatInformation> getPatInformationByAllInformation(
          String patId, String lastName, String firstName, LocalDate birthday, String gender, String address, String phone
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
        birthDayGenderDTO.setGender(patInformation.getGender().name());
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


}
