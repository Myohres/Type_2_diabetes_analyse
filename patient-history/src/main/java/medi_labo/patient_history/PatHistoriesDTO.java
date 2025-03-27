package medi_labo.patient_history;

import java.util.List;

public class PatHistoriesDTO {

    private String patId;

    private String patient;

    private List<String> noteListHistories;

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public List<String> getNoteListHistories() {
        return noteListHistories;
    }

    public void setNoteListHistories(List<String> noteListHistories) {
        this.noteListHistories = noteListHistories;
    }
}

