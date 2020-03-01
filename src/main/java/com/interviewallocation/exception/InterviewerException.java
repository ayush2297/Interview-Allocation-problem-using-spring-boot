package com.interviewallocation.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class InterviewerException extends RuntimeException {
    @Getter
    private HttpStatus statusCode;

    public InterviewerException(String message) {
        super(message);
    }
}
