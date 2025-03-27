package medi_labo.patient_assessment.integration;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        switch (status) {
            case NOT_FOUND: // 404 - L'ID demandé n'existe pas
                return new HttpClientErrorException(HttpStatus.NOT_FOUND, "Ressource non trouvée pour " + methodKey);
            case INTERNAL_SERVER_ERROR: // 500 - Erreur serveur
                return new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne du service B");
            default:
                return FeignException.errorStatus(methodKey, response);
        }
    }
}
