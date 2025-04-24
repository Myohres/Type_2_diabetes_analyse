package medi_labo.patient_history;

import medi_labo.patient_history.controller.PatHistoryController;
import medi_labo.patient_history.model.DTO.PatHistoriesDTO;
import medi_labo.patient_history.model.DTO.PatHistoriesNoteDTO;
import medi_labo.patient_history.model.entity.PatHistory;
import medi_labo.patient_history.service.PatHistoryService;
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

import static org.mockito.Mockito.when;

@WebMvcTest(PatHistoryController.class)
class PatHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatHistoryService patHistoryService;

    String URL_PATIENT_HISTORY = "/pat-history";
    List<PatHistory> patHistoryList;
    PatHistory patHistory1;
    PatHistory patHistory2;

    PatHistoriesDTO patHistoriesDTO1;
    PatHistoriesNoteDTO patHistoriesNoteDTO;
    List<PatHistoriesNoteDTO> patHistoriesNoteDTOList;
    @BeforeEach
    public void setUp() {
        patHistory1 = new PatHistory();
        patHistory1.setPatId("1");
        patHistory1.setPatient("Patient1");
        patHistory1.setNote("note1");

        patHistory2 = new PatHistory();
        patHistory2.setPatId("1");
        patHistory2.setPatient("Patient1");
        patHistory2.setNote("note2");

        patHistoryList = new ArrayList<>();
        patHistoryList.add(patHistory1);
        patHistoryList.add(patHistory2);

        patHistoriesNoteDTO = new PatHistoriesNoteDTO();
        patHistoriesNoteDTO.setId("1");
        patHistoriesNoteDTO.setNote("note1");

        patHistoriesNoteDTOList = new ArrayList<>();
        patHistoriesNoteDTOList.add(patHistoriesNoteDTO);

        patHistoriesDTO1 = new PatHistoriesDTO();
        patHistoriesDTO1.setPatId("1");
        patHistoriesDTO1.setPatient("Patient1");
        patHistoriesDTO1.setNoteListHistories(patHistoriesNoteDTOList);
    }

    @Test
    void findAll() throws Exception {
        when(patHistoryService.getAllPatHistory()).thenReturn(patHistoryList);
        mockMvc.perform(get(URL_PATIENT_HISTORY)).andExpect(status().isOk());
    }

    @Test
    void getPatHistoryById() throws Exception {
        when(patHistoryService.getPatHistoryById(any())).thenReturn(patHistory1);
        mockMvc.perform(get(URL_PATIENT_HISTORY + "/id")
                .param("id","1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPatHistoryByIdNotFound() throws Exception {
        when(patHistoryService.getPatHistoryById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_HISTORY + "/id")
                        .param("id","1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPatHistoryByPatId() throws Exception {
        when(patHistoryService.getPatHistoryByPatId(any())).thenReturn(patHistoriesDTO1);
        mockMvc.perform(get(URL_PATIENT_HISTORY + "/patId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPatHistoryByPatIdNotFound() throws Exception {
        when(patHistoryService.getPatHistoryByPatId(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_HISTORY + "/patId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNoteListHistoriesByPatId() throws Exception {
        when(patHistoryService.getPatHistoryByPatId(any())).thenReturn(patHistoriesDTO1);
        mockMvc.perform(get(URL_PATIENT_HISTORY + "/noteListHistories/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getNoteListHistoriesByPatIdNotFound() throws Exception {
        when(patHistoryService.getPatHistoryByPatId(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get(URL_PATIENT_HISTORY + "/noteListHistories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addPatHistory() throws Exception {
        when(patHistoryService.addPatHistory(any())).thenReturn(patHistory1);
        mockMvc.perform(post(URL_PATIENT_HISTORY + "/add")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{\n" +
                                        "    \"patId\": \"1\",\n" +
                                        "    \"patient\": \"TestNone\",\n" +
                                        "    \"note\": \"Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé\"\n" +
                                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void addPatHistoryNotValid() throws Exception {
        when(patHistoryService.addPatHistory(any())).thenReturn(patHistory1);
        mockMvc.perform(post(URL_PATIENT_HISTORY + "/add")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{\n" +
                                        "    \"patId\": \"1\",\n" +
                                        "    \"patient\": \"TestNone\",\n" +
                                        "    \"note\": \"\"\n" +
                                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNote() throws Exception {
        when(patHistoryService.updatePatHistory(any(), any())).thenReturn(patHistory1);
        mockMvc.perform(put(URL_PATIENT_HISTORY + "/note")
                .param("id", "1")
                .param("note", "note1"))
                .andExpect(status().isOk());
    }

    @Test
    void addNoteNotFound() throws Exception {
        when(patHistoryService.updatePatHistory(any(), any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put(URL_PATIENT_HISTORY + "/note")
                        .param("id", "1")
                        .param("note", "note1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatHistory() throws Exception {
        when(patHistoryService.getPatHistoryById(any())).thenReturn(patHistory1);
        mockMvc.perform(delete(URL_PATIENT_HISTORY + "/delete")
                .param("id", "1"))
                .andExpect(status().isNoContent());
    }
}