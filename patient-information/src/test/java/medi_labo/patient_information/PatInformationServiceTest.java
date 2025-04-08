package medi_labo.patient_information;

import medi_labo.patient_information.config.IdCounterService;
import medi_labo.patient_information.model.document.Gender;
import medi_labo.patient_information.model.dto.BirthDayGenderDTO;
import medi_labo.patient_information.model.document.PatInformation;
import medi_labo.patient_information.repository.PatInformationRepository;
import medi_labo.patient_information.service.PatInformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatInformationServiceTest {

    PatInformation patInformation;

    @Mock
    private PatInformationRepository patInformationRepository;

    @Mock
    IdCounterService idCounterService;

    @InjectMocks
    private PatInformationService patInformationService;

    @BeforeEach
    public void setUp() {
        patInformation = new PatInformation();
        patInformation.setPatId("0001");
        patInformation.setLastName("LastName");
        patInformation.setFirstName("FirstName");
        patInformation.setBirthDay(LocalDate.of(2000,1,1));
        patInformation.setGender(Gender.M);
        patInformation.setAddress("Address");
        patInformation.setPhone("000-000-0000");
    }
    @Test
    void getAllPatInformation() {
        List<PatInformation> patList = new ArrayList<>();
        patList.add(patInformation);
        when(patInformationRepository.findAll()).thenReturn(patList);
        List<PatInformation> allPatInformation = patInformationService.getAllPatInformation();
        assertEquals(allPatInformation.get(0).getLastName(), patInformation.getLastName());
    }

    @Test
    void getPatInformationByPatId() {
        when(patInformationRepository.findByPatId(any())).thenReturn(Optional.of(patInformation));
        PatInformation patInformation1 = patInformationService.getPatInformationByPatId("1");
        assertEquals(patInformation1.getPatId(),patInformation.getPatId());
    }

    @Test
    void getPatInformationByIdNotFound() {
        when(patInformationRepository.findByPatId(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()-> patInformationService.getPatInformationByPatId("1"));
    }

    @Test
    void getPatInformationByAllInformation() {
        List<PatInformation> patList = new ArrayList<>();
        patList.add(patInformation);
        when(patInformationRepository.findByPatIdOrLastNameOrFirstNameOrBirthDayOrGenderOrAddressOrPhone(
                any(),any(),any(),any(),any(),any(),any())).thenReturn(patList);
        List<PatInformation> patInformationList = patInformationService.getPatInformationByAllInformation(
                "1","","",LocalDate.of(1,1,1),"","","");
        assertEquals(patInformationList.get(0).getLastName(), patInformation.getLastName());
    }

    @Test
    void getPatInformationByAllInformationNotFound() {
        when(patInformationRepository.findByPatIdOrLastNameOrFirstNameOrBirthDayOrGenderOrAddressOrPhone(
                any(), any(),any(),any(),any(),any(),any())).thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class, ()-> patInformationService.getPatInformationByAllInformation(
                "1","","",LocalDate.of(1,1,1),"","",""));
    }

    @Test
    void getBirthDayGenderByPatId() {
        when(patInformationRepository.findByPatId(any())).thenReturn(Optional.of(patInformation));
        BirthDayGenderDTO birthDayGenderDTO = patInformationService.getBirthDayGenderByPatId("0001");
        assertEquals(birthDayGenderDTO.getGender(), patInformation.getGender().name());
        assertEquals(birthDayGenderDTO.getBirthDay(), patInformation.getBirthDay());
    }

    @Test
    void getBirthDayGenderByPatIdNotFound() {
        when(patInformationRepository.findByPatId(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()-> patInformationService.getBirthDayGenderByPatId("0001"));
    }


    @Test
    void addPatInformation() {
        when(idCounterService.generateCounter(any())).thenReturn(1);
        when(patInformationRepository.save(any())).thenReturn(patInformation);
        PatInformation patInformation1 = patInformationService.addPatInformation(patInformation);
        assertEquals(patInformation1.getPatId(),patInformation.getPatId());
        verify(patInformationRepository,times(1)).save(any());
    }

    @Test
    void updatePatInformation() {
        when(patInformationRepository.findByPatId(any())).thenReturn(Optional.of(patInformation));
        when(patInformationRepository.save(any())).thenReturn(patInformation);
        PatInformation patInformation1 = new PatInformation();
        patInformation1.setPatId("0001");
        patInformation1.setLastName("LastName");
        patInformation1.setFirstName("FirstName");
        patInformation1.setBirthDay(LocalDate.of(2000,1,1));
        patInformation1.setGender(Gender.F);
        patInformation1.setAddress("Address");
        patInformation1.setPhone("000-000-0000");
        patInformation = patInformationService.updatePatInformation("1",patInformation1);
        assertSame("F", patInformation.getGender().name());

    }

    @Test
    void deletePatInformation() {
        patInformationRepository.deleteById("1");
        verify(patInformationRepository,times(1)).deleteById(any());
    }

}