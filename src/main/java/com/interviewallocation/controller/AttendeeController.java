package com.interviewallocation.controller;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.service.AttendeeRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@CrossOrigin("*")
@Validated
@RestController
@RequestMapping("/attendee")
public class AttendeeController {

    @Autowired
    private AttendeeRegistrationService registrationService;

    @PostMapping("/add")
    public ResponseEntity<List<Long>> addAttendee(@RequestBody @NotEmpty(message = "list cannot be empty") List<@Valid AttendeeDto> attendees) {
        List<Long> ids = registrationService.addAttendees(attendees);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Attendee>> getAttendees() {
        List<Attendee> attendees = registrationService.getAllAttendees();
        return new ResponseEntity<>(attendees,HttpStatus.OK);
    }
}
