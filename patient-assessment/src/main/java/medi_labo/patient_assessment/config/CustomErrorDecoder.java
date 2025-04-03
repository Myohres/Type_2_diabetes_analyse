package medi_labo.patient_assessment.config;

import feign.Response;
import feign.codec.ErrorDecoder;

import medi_labo.patient_assessment.exception.CustomExceptions;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = "";
        try {
            if (response.body() != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream()));
                errorMessage = reader.readLine();
            }
        } catch (IOException e) {
            errorMessage = "Erreur lors de la lecture du message d'erreur";
        }
        return switch (response.status()) {
            case 400 -> new CustomExceptions.BadRequestException("Erreur 400: Requête incorrecte. "  + errorMessage );
            case 404 -> new CustomExceptions.ResourceNotFoundException("Erreur 404: Ressource non trouvée. " + errorMessage);
            case 500 -> new CustomExceptions.InternalServerErrorException("Erreur 500: Problème interne du serveur. " + errorMessage);
            default -> new Exception("Erreur inconnue: " + response.status());
        };
    }
}

