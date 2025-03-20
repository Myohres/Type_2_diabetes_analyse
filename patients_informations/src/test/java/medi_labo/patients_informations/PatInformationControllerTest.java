package medi_labo.patients_informations;

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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@WebMvcTest(PatInformationController.class)
class PatInformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatInformationService patInformationService;

    List<PatInformation> patInformationList;
    PatInformation patInformation;

    @BeforeEach
    public void setUp() {
        patInformation = new PatInformation();
        patInformation.setPatId("0001");
        patInformation.setLastName("LastName");
        patInformation.setFirstName("FirstName");
        patInformation.setBirthDay("2000-01-01");
        patInformation.setGender("M");
        patInformation.setAddress("Address");
        patInformation.setPhone("000-000-0000");
    }

    @Test
    void getPatientInformation() throws Exception {
        patInformationList = new ArrayList<>();
        patInformationList.add(patInformation);
        when(patInformationService.getAllPatInformation()).thenReturn(patInformationList);
        mockMvc.perform(get("/patient/")).andExpect(status().isOk());
    }

    @Test
    void getPatientInformationNotFound() throws Exception {
        patInformationList = new ArrayList<>();
        when(patInformationService.getAllPatInformation()).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/patient/")).andExpect(status().isNotFound());
    }

    @Test
    void getPatientInformationByPatIdId() throws Exception {
        when(patInformationService.getPatInformationByPatId(any())).thenReturn(patInformation);
        mockMvc.perform(get("/patient/patId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPatientInformationByIdNotFound() throws Exception {
        when(patInformationService.getPatInformationByPatId(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/patient/patId/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void getPatientByAllInformation() throws Exception {
        patInformationList = new ArrayList<>();
        patInformationList.add(patInformation);
        when(patInformationService.getPatInformationByAllInformation(any(),any(),any(),any(),any(),any(),any())).thenReturn(patInformationList);
        mockMvc.perform(get("/patient/information/")
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
        mockMvc.perform(get("/patient/information/")
                .param("lastName", "lastName")
                .param("firstName", "lastName")
                .param("birthDay", "1950-01-01")
                .param("gender", "M")
                .param("address", "lastName")
                .param("phone", "lastName"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPatientAge() throws Exception {
        when(patInformationService.calculateAgePatient(any())).thenReturn(10);
        mockMvc.perform(get("/patient/age/")
                .param("birthDay", "2000-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void createPatient() throws Exception {
        when(patInformationService.addPatInformation(any())).thenReturn(patInformation);
        mockMvc.perform(post("/patient/add/")
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
        mockMvc.perform(put("/patient/update/12")
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
        mockMvc.perform(put("/patient/update/id/")
                        .param("id", "1")
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

        mockMvc.perform(delete("/patient/delete/patId/")
                        .param("patId", "1"))
                .andExpect(status().isNoContent());
        verify(patInformationService, times(1)).deletePatInformation("1");
    }

    @Test
    void deletePatientNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(patInformationService).deletePatInformation(any());
        mockMvc.perform(delete("/patient/delete/patId/")
                        .param("patId", "1"))
                .andExpect(status().isNotFound());
    }
}