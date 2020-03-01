package com.interviewallocation.exception;

import lombok.Getter;

import java.util.List;

public class ErrorResponse {

    @Getter
    private String message;

    @Getter
    private List<String> details;

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}
