package medi_labo.patient_assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PatAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatAssessmentApplication.class, args);
    }

}
