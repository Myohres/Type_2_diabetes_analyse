package medi_labo.patient_historique;

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
class PatHistoriqueServiceTest {

    PatHistorique patHistorique;
    List<PatHistorique> patHistoriquesList;

    @Mock
    private PatHistoriqueRepository patHistoriqueRepository;

    @InjectMocks
    private PatHistoriqueService patHistoriqueService;

    @BeforeEach
    public void setUp() {
        patHistoriquesList = new ArrayList<>();
        patHistorique = new PatHistorique();
        patHistorique.setId("1");
        patHistorique.setPatId("01");
        patHistorique.setPatient("patient");
        patHistorique.setNote("note");
    }

    @Test
    void getPatAllHistorique() {
        patHistoriquesList.add(patHistorique);
        when(patHistoriqueRepository.findAll()).thenReturn(patHistoriquesList);
        List<PatHistorique> historiqueList = patHistoriqueService.getAllPatHistorique();
        assertEquals(patHistoriquesList, historiqueList);
    }

    @Test
    void getPatAllHistoriqueNoFound() {
        when(patHistoriqueRepository.findAll()).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> patHistoriqueService.getAllPatHistorique());
    }

    @Test
    void getPatHistoriqueById() {
        when(patHistoriqueRepository.findById(any())).thenReturn(Optional.of(patHistorique));
        PatHistorique patHistorique1 = patHistoriqueService.getPatHistoriqueById(patHistorique.getId());
        assertEquals(patHistorique, patHistorique1);
    }

    @Test
    void getPatHistoriqueByPatIdNotFound() {
        when(patHistoriqueRepository.findByPatId(any())).thenReturn(patHistoriquesList);
        assertThrows(NoSuchElementException.class, () -> patHistoriqueService.getPatHistoriquesByPatId("01"));
    }

    @Test
    void getPatHistoriquesByPatientName() {
        patHistoriquesList.add(patHistorique);
        when(patHistoriqueRepository.findByPatient(any())).thenReturn(patHistoriquesList);
        List<PatHistorique> historiqueList = patHistoriqueService.getPatHistoriquesByPatientName("patient");
        assertEquals(patHistoriquesList, historiqueList);
    }

    @Test
    void getPatHistoriquesByPatientNameNoFound() {
        when(patHistoriqueRepository.findByPatient(any())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> patHistoriqueService.getPatHistoriquesByPatientName("patient"));
    }

    @Test
    void getPatHistoriquesByPatId() {
        patHistoriquesList.add(patHistorique);
        when(patHistoriqueRepository.findByPatId(any())).thenReturn(patHistoriquesList);
        List<PatHistorique> historiqueList = patHistoriqueService.getPatHistoriquesByPatId("01");
        assertEquals(patHistoriquesList, historiqueList);
    }

    @Test
    void getPatHistoriquesByPatIdNoFound() {
        when(patHistoriqueRepository.findByPatId(any())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> patHistoriqueService.getPatHistoriquesByPatId("01"));
    }

    @Test
    void addPatHistorique() {
        when(patHistoriqueRepository.save(any())).thenReturn(patHistorique);
        PatHistorique patHistorique1 = patHistoriqueService.addPatHistorique(patHistorique);
        assertEquals(patHistorique, patHistorique1);
    }

    @Test
    void addNote() {
        when(patHistoriqueRepository.save(any())).thenReturn(patHistorique);
        PatHistorique patHistorique1 = patHistoriqueService.addNote("01", "patient","note");
        assertEquals(patHistorique, patHistorique1);
    }

    @Test
    void updatePatHistorique() {
        when(patHistoriqueRepository.findById(any())).thenReturn(Optional.of(patHistorique));
        when(patHistoriqueRepository.save(any())).thenReturn(patHistorique);
        PatHistorique patHistorique1 = patHistoriqueService.updatePatHistorique("1", "note1");
        assertEquals(patHistorique, patHistorique1);

    }

    @Test
    void deletePatHistorique() {
        patHistoriqueService.deletePatHistorique("1");
        verify(patHistoriqueRepository,times(1)).deleteById(any());
    }
}