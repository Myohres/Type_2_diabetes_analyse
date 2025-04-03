package medi_labo.patient_assessment.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(5000, 30000);
    }

    @Bean
    public Logger feignLogger() {
        return new Logger.JavaLogger().appendToFile("feign.log");
    }
}