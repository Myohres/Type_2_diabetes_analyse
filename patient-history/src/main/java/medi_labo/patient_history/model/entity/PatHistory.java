package medi_labo.patient_history.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "patient-history")
public class PatHistory {

    @Id
    private String id;
    @NotBlank(message = "patId est obligatoire")
    private String patId;
    @NotBlank(message = "patient est obligatoire")
    private String patient;
    @NotBlank(message = "note est obligatoire")
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
