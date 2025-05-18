package medi_labo.patient_assessment.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            if (ife.getTargetType() == java.time.LocalDate.class) {
                log.error("Date de naissance invalide reçue : {}", ife.getValue());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Format de date invalide pour 'birthDay'. Format attendu : yyyy-MM-dd");
            }
        }

        log.error("Erreur de désérialisation JSON : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Requête mal formée ou invalide.");
    }

    @ExceptionHandler(CustomExceptions.ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(CustomExceptions.ResourceNotFoundException ex) {
        log.error("Ressource non trouvée : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(CustomExceptions.BadRequestException ex) {
        log.error("Bad request error : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.InternalServerErrorException.class)
    public ResponseEntity<String> handleInternalError(CustomExceptions.InternalServerErrorException ex) {
        log.error("Erreur interne : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        log.error("Exception non gérée : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inattendue : " + ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ServiceUnavailableException.class)
    public ResponseEntity<String> handleServiceUnavailable(Exception ex) {
        log.error("Service indisponible");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service indisponible : " + ex.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleGenericFeignException(FeignException ex) {
        log.error("Erreur Feign non gérée : status={} - {}", ex.status(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Erreur de communication avec un service distant.");
    }
}
