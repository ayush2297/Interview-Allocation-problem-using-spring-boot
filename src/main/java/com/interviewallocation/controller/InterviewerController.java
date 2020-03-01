package com.interviewallocation.controller;

import com.interviewallocation.dto.InterviewerDto;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.service.InterviewerRegistrationService;
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
@RequestMapping("/interviewer")
public class InterviewerController {

    @Autowired
    private InterviewerRegistrationService registrationService;

    @PostMapping("/add")
    public ResponseEntity<List<Long>> addInterviewers(@RequestBody @NotEmpty(message = "list cannot be empty") List<@Valid InterviewerDto> interviewers) {
        List<Long> ids = registrationService.addAllInterviewers(interviewers);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Interviewer>> getInterviewers() {
        List<Interviewer> interviews = registrationService.getAllInterviewers();
        return new ResponseEntity<>(interviews,HttpStatus.OK);
    }

}
