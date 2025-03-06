package medi_labo.patient_bilan;

import java.util.List;

public class RequestBilan {

    private String patId;
    private List<String> patientNoteList;
    private String birthDay;
    private String gender;

    public String getPatId() { return patId; }
    public void setPatId(String patId) { this.patId = patId; }

    public List<String> getPatientNoteList() { return patientNoteList; }
    public void setPatientNoteList(List<String> patientNoteList) { this.patientNoteList = patientNoteList; }

    public String getBirthDay() { return birthDay; }
    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}