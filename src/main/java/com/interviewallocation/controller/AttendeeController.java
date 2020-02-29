package com.interviewallocation.controller;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.service.AttendeeRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/attendee")
public class AttendeeController {

    @Autowired
    private AttendeeRegistrationService registrationService;

    @PostMapping("/add")
    public ResponseEntity<List<Long>> addAttendee(@RequestBody List<AttendeeDto> attendees) {
        List<Long> ids = registrationService.addAttendees(attendees);
        return new ResponseEntity<List<Long>>(ids, HttpStatus.OK);
    }
}
