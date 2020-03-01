package com.interviewallocation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class InterviewSchedulerException extends RuntimeException {
    @Getter
    private HttpStatus statusCode;

    @Getter
    private String message;

    public InterviewSchedulerException(String message, HttpStatus statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
