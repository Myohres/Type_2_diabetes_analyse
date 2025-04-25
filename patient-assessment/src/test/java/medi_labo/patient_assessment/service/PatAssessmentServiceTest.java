package medi_labo.patient_assessment.service;

import medi_labo.patient_assessment.model.Entity.PatAssessment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatAssessmentServiceTest {

    @InjectMocks
    PatAssessmentService patAssessmentService = new PatAssessmentService();

    String patId;
    List<String> patientNoteList = new ArrayList<>();
    LocalDate birthDay = LocalDate.now();

    @Test
    void generatePatAssessment() {
        PatAssessment patAssessment = patAssessmentService.generatePatAssessment("1",patientNoteList, birthDay, "M");
        assertEquals("1", patAssessment.getPatId());
        assertEquals("None", patAssessment.getRiskLevel());
    }

    @Test
    void calculateAgePatient() {
        Integer age = patAssessmentService.calculateAgePatient(birthDay);
        assertEquals(0, age);
    }

    @Test
    void getTriggerWordNumber() {
        patientNoteList.add("Poids");
        patientNoteList.add("Taille");
        Integer triggerWordNumber = patAssessmentService.getTriggerWordNumber(patientNoteList);
        assertEquals(2, triggerWordNumber);
    }

    @Test
    void evaluationRiskLevelAgeMore30triggerWordUnder2() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(1, 31, "M");
        assertEquals("None", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeMore30triggerWordUnder6() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(5, 31, "M");
        assertEquals( "Borderline", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeMore30triggerWordUnder8() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(7, 31, "M");
        assertEquals("In danger", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeMore30triggerWordMore7() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(8, 31, "M");
        assertEquals("Early onset", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeUnder31GenderMTriggerWordUnder3() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(2, 29, "M");
        assertEquals("None", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeUnder31GenderMTriggerWordUnder5() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(4, 29, "M");
        assertEquals("In danger", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeUnder31GenderMTriggerWordMore4() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(5, 29, "M");
        assertEquals("Early onset", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeUnder31GenderFTriggerWordUnder4() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(3, 29, "F");
        assertEquals("None" ,riskLevel);

    }
    @Test
    void evaluationRiskLevelAgeUnder31GenderFTriggerWordUnder7() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(6, 30, "F");
        assertEquals("In danger", riskLevel);
    }

    @Test
    void evaluationRiskLevelAgeUnder31GenderFTriggerWordMore6() {
        String riskLevel = patAssessmentService.evaluationRiskLevel(7, 29, "F");
        assertEquals("Early onset", riskLevel);
    }
}