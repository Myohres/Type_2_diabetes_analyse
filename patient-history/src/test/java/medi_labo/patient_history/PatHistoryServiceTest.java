package medi_labo.patient_history;

import medi_labo.patient_history.model.DTO.PatHistoriesDTO;
import medi_labo.patient_history.model.DTO.PatHistoryToAddDTO;
import medi_labo.patient_history.model.entity.PatHistory;
import medi_labo.patient_history.repository.PatHistoryRepository;
import medi_labo.patient_history.service.PatHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatHistoryServiceTest {

    PatHistory patHistory;
    List<PatHistory> patHistoriquesList;

    @Mock
    private PatHistoryRepository patHistoryRepository;

    @InjectMocks
    private PatHistoryService patHistoryService;

    @BeforeEach
    public void setUp() {
        patHistoriquesList = new ArrayList<>();
        patHistory = new PatHistory();
        patHistory.setId("1");
        patHistory.setPatId("01");
        patHistory.setPatient("patient");
        patHistory.setNote("note");
    }

    @Test
    void getPatAllHistorique() {
        patHistoriquesList.add(patHistory);
        when(patHistoryRepository.findAll()).thenReturn(patHistoriquesList);
        List<PatHistory> historiqueList = patHistoryService.getAllPatHistory();
        assertEquals(patHistoriquesList, historiqueList);
    }

    @Test
    void getPatAllHistoriqueNoFound() {
        when(patHistoryRepository.findAll()).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> patHistoryService.getAllPatHistory());
    }

    @Test
    void getPatHistoryById() {
        when(patHistoryRepository.findById(any())).thenReturn(Optional.of(patHistory));
        PatHistory patHistory1 = patHistoryService.getPatHistoryById(patHistory.getId());
        assertEquals(patHistory, patHistory1);
    }

    @Test
    void getPatHistoriqueByPatIdNotFound() {
        when(patHistoryRepository.findByPatId(any())).thenReturn(patHistoriquesList);
        assertThrows(NoSuchElementException.class, () -> patHistoryService.getPatHistoryByPatId("01"));
    }

    @Test
    void getPatHistoryByPatId() {
        patHistoriquesList.add(patHistory);
        when(patHistoryRepository.findByPatId(any())).thenReturn(patHistoriquesList);
        PatHistoriesDTO historiqueList = patHistoryService.getPatHistoryByPatId("01");
        assertEquals(patHistoriquesList.get(0).getPatId(), historiqueList.getPatId());
        assertEquals(patHistoriquesList.get(0).getPatient(), historiqueList.getPatient());
        assertEquals(patHistoriquesList.get(0).getNote(), historiqueList.getNoteListHistories().get(0).getNote());
    }

    @Test
    void getPatHistoryByPatIdNoFound() {
        when(patHistoryRepository.findByPatId(any())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> patHistoryService.getPatHistoryByPatId("01"));
    }

    @Test
    void addPatHistory() {
        PatHistoryToAddDTO patHistoryToAddDTO = new PatHistoryToAddDTO();
        when(patHistoryRepository.save(any())).thenReturn(patHistory);
        PatHistory patHistory1 = patHistoryService.addPatHistory(patHistoryToAddDTO);
        assertEquals(patHistory, patHistory1);
    }

    @Test
    void updatePatHistory() {
        when(patHistoryRepository.findById(any())).thenReturn(Optional.of(patHistory));
        when(patHistoryRepository.save(any())).thenReturn(patHistory);
        PatHistory patHistory1 = patHistoryService.updatePatHistory("1", "note1");
        assertEquals(patHistory, patHistory1);

    }

    @Test
    void deletePatHistory() {
        patHistoryService.deletePatHistory("1");
        verify(patHistoryRepository,times(1)).deleteById(any());
    }
}