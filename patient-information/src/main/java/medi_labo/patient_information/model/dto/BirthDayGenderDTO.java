package medi_labo.patient_information.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BirthDayGenderDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthDay est obligatoire")
    private LocalDate birthDay;
    @NotNull(message = "gender est obligatoire")
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
