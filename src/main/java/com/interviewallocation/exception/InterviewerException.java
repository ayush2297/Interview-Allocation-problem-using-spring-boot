package com.interviewallocation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class InterviewerException extends RuntimeException {
    @Getter
    private String message;
    @Getter
    private HttpStatus statusCode;

    public InterviewerException(String message, HttpStatus statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
