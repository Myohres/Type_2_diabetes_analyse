package medi_labo.patient_history.model;

import java.util.List;

public class PatHistoriesDTO {

    private String patId;

    private String patient;

    private List<PatHistoriesNoteDTO> noteListHistories;

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

    public List<PatHistoriesNoteDTO> getNoteListHistories() {
        return noteListHistories;
    }

    public void setNoteListHistories(List<PatHistoriesNoteDTO> noteListHistories) {
        this.noteListHistories = noteListHistories;
    }
}

