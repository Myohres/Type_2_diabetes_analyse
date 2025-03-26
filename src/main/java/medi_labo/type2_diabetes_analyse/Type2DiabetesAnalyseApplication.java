package medi_labo.type2_diabetes_analyse;

import medi_labo.patient_assessment.PatAssessmentApplication;
import medi_labo.patient_history.PatHistoryApplication;
import medi_labo.patient_information.PatInformationApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Type2DiabetesAnalyseApplication {

    public static void main(String[] args) {
        SpringApplication.run(Type2DiabetesAnalyseApplication.class, args);
        SpringApplication.run(PatInformationApplication.class, args);
        SpringApplication.run(PatHistoryApplication.class, args);
        SpringApplication.run(PatAssessmentApplication.class, args);
    }



}
