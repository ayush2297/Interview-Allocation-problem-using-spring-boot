package com.interviewallocation.controller;

import com.interviewallocation.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(RuntimeException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AttendeesNotFoundException.class)
    public final ResponseEntity<Object> handleAttendeesNotFoundException(AttendeesNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), details);
        return noContent().build();
    }

    @ExceptionHandler(value = InterviewRoomException.class)
    public final ResponseEntity<Object> handleInterviewRoomException(InterviewRoomException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Duplicate Records Found. Please enter unique data", details);
        return new ResponseEntity(error, ex.getStatusCode());
    }

    @ExceptionHandler(value = InterviewerException.class)
    public final ResponseEntity<Object> handleInterviewerException(InterviewerException ex ,WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity(error, ex.getStatusCode());
    }

    @ExceptionHandler(value = InterviewSchedulerException.class)
    public final ResponseEntity<Object> handleInterviewSchedulerException(InterviewSchedulerException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(),null);
        return new ResponseEntity(error, ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}