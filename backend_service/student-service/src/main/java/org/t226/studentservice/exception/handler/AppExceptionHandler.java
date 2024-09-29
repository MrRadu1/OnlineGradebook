package org.t226.studentservice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.t226.studentservice.exception.AppException;
import org.t226.studentservice.exception.ErrorMessage;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<Object> handleError(AppException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                exception.getExceptionType()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
