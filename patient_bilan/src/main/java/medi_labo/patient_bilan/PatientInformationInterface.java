package medi_labo.patient_bilan;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="patients_informations")
public interface PatientInformationInterface {

    @GetMapping("/patient/birthdate{id}")
        String getBirthDateByid(@PathVariable String id);
    }

