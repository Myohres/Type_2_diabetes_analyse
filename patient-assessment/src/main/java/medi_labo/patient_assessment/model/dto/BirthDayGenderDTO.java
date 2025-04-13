package medi_labo.patient_assessment.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BirthDayGenderDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "birthDay est obligatoire")
    private LocalDate birthDay;
    @NotBlank(message = "gender est obligatoire")
    @Pattern(
            regexp = "M|F",
            message = "Le genre doit être 'M' ou 'F'"
    )
    private String gender;

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
