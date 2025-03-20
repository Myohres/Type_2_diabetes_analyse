package medi_labo.patients_informations;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(collection = "PatInformation")
public class PatInformation {

    @Id
    private String patId;

    @NotBlank(message = "lastName est obligatoire")
    private String lastName;

    @NotBlank(message = "firstName est obligatoire")
    private String firstName;

    @NotBlank(message = "birthDay est obligatoire")
    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "La date de naissance doit être au format YYYY-MM-DD"
    )
    private String birthDay;

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

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
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
