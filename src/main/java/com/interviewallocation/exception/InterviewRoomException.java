package com.interviewallocation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class InterviewRoomException extends RuntimeException {
    @Getter
    private final String message;

    @Getter
    private final HttpStatus statusCode;

    public InterviewRoomException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
