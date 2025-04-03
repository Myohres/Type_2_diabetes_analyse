package medi_labo.patient_assessment.dto;

import java.time.LocalDate;
import java.util.List;

public class RequestPatAssessment {

    private String patId;
    private List<String> patientNoteList;
    private LocalDate birthDay;
    private String gender;

    public String getPatId() { return patId; }
    public void setPatId(String patId) { this.patId = patId; }

    public List<String> getPatientNoteList() { return patientNoteList; }
    public void setPatientNoteList(List<String> patientNoteList) { this.patientNoteList = patientNoteList; }

    public LocalDate getBirthDay() { return birthDay; }
    public void setBirthDay(LocalDate birthDay) { this.birthDay = birthDay; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}