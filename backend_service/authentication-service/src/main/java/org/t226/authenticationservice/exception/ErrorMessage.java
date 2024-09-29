package org.t226.authenticationservice.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private final String message;
    private final LocalDateTime timestamp;
    private final HttpStatus httpRequest;
    private final ExceptionType exceptionType;
}