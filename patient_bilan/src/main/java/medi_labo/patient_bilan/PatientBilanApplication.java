package medi_labo.patient_bilan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PatientBilanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientBilanApplication.class, args);
    }

}
