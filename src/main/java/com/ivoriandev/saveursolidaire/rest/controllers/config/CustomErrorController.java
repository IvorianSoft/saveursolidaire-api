package com.ivoriandev.saveursolidaire.rest.controllers.config;

import com.ivoriandev.saveursolidaire.utils.dto.eroor.CustomErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
@RestControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorDto> handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());

        return ResponseEntity.status(e.getStatusCode()).body(
                CustomErrorDto.builder()
                        .title(status.getReasonPhrase())
                        .status(status.value())
                        .message(e.getReason())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
