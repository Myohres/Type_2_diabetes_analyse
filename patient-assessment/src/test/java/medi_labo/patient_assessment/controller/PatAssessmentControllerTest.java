package medi_labo.patient_assessment.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
import medi_labo.patient_assessment.exception.CustomExceptions;
import medi_labo.patient_assessment.integration.PatHistoryClient;
import medi_labo.patient_assessment.integration.PatInformationClient;
import medi_labo.patient_assessment.model.Entity.PatAssessment;
import medi_labo.patient_assessment.model.dto.BirthDayGenderDTO;
import medi_labo.patient_assessment.service.PatAssessmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;




import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatAssessmentController.class)
class PatAssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PatInformationClient patInformationClient;

    @MockBean
    PatHistoryClient patHistoryClient;

    @MockBean
    PatAssessmentService patAssessmentService;

    BirthDayGenderDTO birthDayGenderDTO;
    List<String> noteListHistories;

    @BeforeEach
    void setup() {
        birthDayGenderDTO = new BirthDayGenderDTO();
        birthDayGenderDTO.setBirthDay(LocalDate.of(1980, 5, 10));
        birthDayGenderDTO.setGender("F");

        noteListHistories = List.of("Note 1", "Note 2");
    }

    @Test
    void getPatAssessmentByPatId() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenReturn(ResponseEntity.ok(birthDayGenderDTO));
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        when(patAssessmentService.generatePatAssessment(any(), any(), any(), any())).thenReturn(new PatAssessment());
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isOk());
    }

    @Test
    void getPatAssessmentByPatIdWhenPatInformationNotAvailable() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenReturn(ResponseEntity.badRequest().build());
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isBadRequest());
    }

    @Test
    void getPatAssessmentByPatIdWhenServiceNotAvailable() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenThrow(new CustomExceptions.ServiceUnavailableException("not found"));
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isServiceUnavailable());
    }

    @Test
    void getPatAssessmentByPatIdWhenPatInformationNotFound() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenThrow(new CustomExceptions.ResourceNotFoundException("not found"));
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isNotFound());
    }

    @Test
    void getPatAssessmentByPatIdWhenPatInformationWithGenderWrongFormat() throws Exception {
        birthDayGenderDTO = new BirthDayGenderDTO();
        birthDayGenderDTO.setGender("A");
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenReturn(ResponseEntity.ok(birthDayGenderDTO));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isBadRequest());
    }

    @Test
    void getPatAssessmentByPatIdWhenPatHistoryNotFound() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenReturn(ResponseEntity.ok(birthDayGenderDTO));
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenThrow(new CustomExceptions.ResourceNotFoundException("not found"));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isNotFound());
    }

    @Test
    void getPatAssessmentByPatIdWhenLocaleDateNotValid() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenThrow(HttpMessageNotReadableException.class);
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isBadRequest());
    }

    @Test
    void getPatAssessmentByPatIdWhenBadRequest() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenThrow(CustomExceptions.BadRequestException.class);
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isBadRequest());
    }

    @Test
    void getPatAssessmentByPatIdWhenInternalError() throws Exception {
        when(patInformationClient.getBirthDayGenderByPatId(any())).thenThrow(CustomExceptions.InternalServerErrorException.class);
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isInternalServerError());
    }

    @Test
    void getPatAssessmentByPatIdWhenFeignException() throws Exception {
        birthDayGenderDTO = new BirthDayGenderDTO();

        when(patInformationClient.getBirthDayGenderByPatId(any())).thenThrow(FeignException.class);
        when(patHistoryClient.getNoteListHistoriesByPatId(any())).thenReturn(ResponseEntity.ok(noteListHistories));
        mockMvc.perform(get("/pat-assessment/patId/1")).andExpect(status().isBadGateway());
    }
}