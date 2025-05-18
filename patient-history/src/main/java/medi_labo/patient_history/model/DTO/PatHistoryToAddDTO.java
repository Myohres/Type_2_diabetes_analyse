package medi_labo.patient_history.model.DTO;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public class PatHistoryToAddDTO {

    @NotBlank(message = "patId est obligatoire")
    private String patId;
    @NotBlank(message = "patient est obligatoire")
    private String patient;
    @NotBlank(message = "note est obligatoire")
    private String note;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
