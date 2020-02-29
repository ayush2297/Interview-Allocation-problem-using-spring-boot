package com.interviewallocation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class AttendeesNotFoundException extends RuntimeException {
    public AttendeesNotFoundException(String message) {
        super(message);
    }
}
