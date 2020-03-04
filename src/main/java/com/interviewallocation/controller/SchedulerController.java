package com.interviewallocation.controller;

import com.interviewallocation.dto.OutputDto;
import com.interviewallocation.service.InterviewSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Validated
@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    private InterviewSchedulerService schedulerService;

    public SchedulerController() {
    }

    public SchedulerController(InterviewSchedulerService service) {
        schedulerService = service;
    }

    @GetMapping("/")
    public ResponseEntity<OutputDto> getInterviews() {
        OutputDto interviews = schedulerService.getAllInterviews();
        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }
}
