package org.t226.studentservice.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException{

    private final ExceptionType exceptionType;
    public AppException(String s, ExceptionType exceptionType) {
        super(s);
        this.exceptionType = exceptionType;
    }
}
