package com.docTime.validation;

import com.docTime.apiResponse.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ValidatorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(ValidationException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getType() == ErrorType.RESOURCE_NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getType() == ErrorType.BUSINESS_ERROR) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        log.warn("Incident contrôlé [{}] : {}", ex.getType().getCode(), ex.getMessage());

        Map<String, String> errorDetails = Map.of("errorCode", ex.getType().getCode());

        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>(
                false,
                ex.getMessage(),
                LocalDateTime.now(),
                errorDetails
        );

        return ResponseEntity.status(status).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleUnknownException(Exception ex) {

        log.error("Crash inattendu de l'application : ", ex);

        Map<String, String> errorDetails = Map.of("errorCode", ErrorType.SERVER_ERROR.getCode());

        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>(
                false,
                "Une erreur interne au serveur est survenue.",
                LocalDateTime.now(),
                errorDetails
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
