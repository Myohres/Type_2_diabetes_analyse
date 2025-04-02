package medi_labo.patient_information.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "patient-information")
public class PatInformation {

    @Id
    private String patId;

    @NotBlank(message = "lastName est obligatoire")
    private String lastName;

    @NotBlank(message = "firstName est obligatoire")
    private String firstName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthDay est obligatoire")
    private LocalDate birthDay;

    @NotBlank(message = "gender est obligatoire")
    @Pattern(
            regexp = "M|F",
            message = "Le genre doit être 'M' ou 'F'"
    )
    private String gender;

    private String address;
    private String phone;

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
