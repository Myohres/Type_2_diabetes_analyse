package medi_labo.patient_information;

import medi_labo.patient_information.controller.PatInformationController;
import medi_labo.patient_information.model.dto.BirthDayGenderDTO;
import medi_labo.patient_information.model.document.Gender;
import medi_labo.patient_information.model.document.PatInformation;
import medi_labo.patient_information.service.PatInformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@WebMvcTest(PatInformationController.class)
class PatInformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatInformationService patInformationService;

    String URL_PATIENT_INFORMATION = "/pat-information";
    List<PatInformation> patInformationList;
    PatInformation patInformation;

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
    void getPatientInformation() throws Exception {
        patInformationList = new ArrayList<>();
        patInformationList.add(patInformation);
        when(patInformationService.getAllPatInformation()).thenReturn(patInformationList);
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/")).andExpect(status().isOk());
    }

    @Test
    void getPatientInformationNotFound() throws Exception {
        patInformationList = new ArrayList<>();
        when(patInformationService.getAllPatInformation()).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/")).andExpect(status().isNotFound());
    }

    @Test
    void getPatientInformationByPatIdId() throws Exception {
        when(patInformationService.getPatInformationByPatId(any())).thenReturn(patInformation);
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/patId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPatientInformationByIdNotFound() throws Exception {
        when(patInformationService.getPatInformationByPatId(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/patId/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void getPatientByAllInformation() throws Exception {
        patInformationList = new ArrayList<>();
        patInformationList.add(patInformation);
        when(patInformationService.getPatInformationByAllInformation(any(),any(),any(),any(),any(),any(),any())).thenReturn(patInformationList);
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/information/")
                        .param("lastName", "lastName")
                        .param("firstName", "lastName")
                        .param("birthDay", "1950-12-01")
                        .param("gender", "F")
                        .param("address", "lastName")
                        .param("phone", "lastName"))
                .andExpect(status().isOk());
    }

    @Test
    void getPatientByAllInformationNotFound() throws Exception {
        patInformationList = new ArrayList<>();
        patInformationList.add(patInformation);
        when(patInformationService.getPatInformationByAllInformation(any(),any(),any(),any(),any(),any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_INFORMATION +"/information/")
                .param("lastName", "lastName")
                .param("firstName", "lastName")
                .param("birthDay", "1950-01-01")
                .param("gender", "M")
                .param("address", "lastName")
                .param("phone", "lastName"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBirthDayGenderByPatIdFound() throws Exception {
        BirthDayGenderDTO birthDayGenderDTO = new BirthDayGenderDTO();
        birthDayGenderDTO.setBirthDay(LocalDate.of(2000,1,1));
        birthDayGenderDTO.setGender("M");
        when(patInformationService.getBirthDayGenderByPatId(any())).thenReturn(birthDayGenderDTO);
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/1/birthDayGender"))
                .andExpect(status().isOk());
    }

    @Test
    void getBirthDayGenderByPatIdNotFound() throws Exception {
        when(patInformationService.getBirthDayGenderByPatId(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_INFORMATION+"/1/birthDayGender"))
                .andExpect(status().isNotFound());
    }


    @Test
    void createPatient() throws Exception {
        when(patInformationService.addPatInformation(any())).thenReturn(patInformation);
        mockMvc.perform(post(URL_PATIENT_INFORMATION + "/add/")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{\n" +
                                        "\"patId\": \"10\",\n" +
                                        "    \"lastName\": \"TestNone\",\n" +
                                        "    \"firstName\": \"Test\",\n" +
                                        "    \"birthDay\": \"1966-12-31\",\n" +
                                        "  \t\"gender\" : \"F\",\n" +
                                        "\t\"address\" : \"1 Brookside St\",\n" +
                                        "  \t\"phone\" : \"100-222-3333\"\n" +
                                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePatient() throws Exception {
        when(patInformationService.updatePatInformation(any(), any())).thenReturn(patInformation);
        mockMvc.perform(put(URL_PATIENT_INFORMATION + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{\n" +
                                        "\"patId\": \"10\",\n" +
                                        "    \"lastName\": \"TestNone\",\n" +
                                        "    \"firstName\": \"Test\",\n" +
                                        "    \"birthDay\": \"1966-12-31\",\n" +
                                        "  \t\"gender\" : \"F\",\n" +
                                        "\t\"address\" : \"1 Brookside St\",\n" +
                                        "  \t\"phone\" : \"100-222-3333\"\n" +
                                        "}"))
                .andExpect(status().isOk());
    }
    @Test
    void updatePatientNotFound() throws Exception {
        when(patInformationService.updatePatInformation(any(), any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put(URL_PATIENT_INFORMATION + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{\n" +
                                        "    \"lastName\": \"TestNone\",\n" +
                                        "    \"firstName\": \"Test\",\n" +
                                        "    \"birthDay\": \"1966-12-31\",\n" +
                                        "  \t\"gender\" : \"F\",\n" +
                                        "\t\"address\" : \"1 Brookside St\",\n" +
                                        "  \t\"phone\" : \"100-222-3333\"\n" +
                                        "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatient() throws Exception {
        when(patInformationService.getPatInformationByPatId(any())).thenReturn(patInformation);
        mockMvc.perform(delete(URL_PATIENT_INFORMATION + "/delete/1"))
                .andExpect(status().isNoContent());
        verify(patInformationService, times(1)).deletePatInformation("1");
    }

    @Test
    void deletePatientNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(patInformationService).deletePatInformation(any());
        mockMvc.perform(delete(URL_PATIENT_INFORMATION + "/delete/1"))
                .andExpect(status().isNotFound());
    }
}