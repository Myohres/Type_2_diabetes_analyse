package medi_labo.patient_history.model.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class PatHistoriesDTO {

    @NotBlank(message = "patId est obligatoire")
    private String patId;
    @NotBlank(message = "patient est obligatoire")
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

