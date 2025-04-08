package medi_labo.patient_information.model.dto;

import java.time.LocalDate;

public class BirthDayGenderDTO {

    private LocalDate birthDay;
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
