package com.interviewallocation.controller;

import com.interviewallocation.dto.RoomDto;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.service.RoomRegistrationService;
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
@RequestMapping("/interview-room")
public class InterviewRoomController {

    @Autowired
    private RoomRegistrationService registrationService;

    @PostMapping("/add")
    public ResponseEntity<List<Long>> addRooms(@RequestBody @NotEmpty(message = "list cannot be empty") List<@Valid RoomDto> rooms) {
        List<Long> roomIds = registrationService.addInterviewRooms(rooms);
        return new ResponseEntity<>(roomIds, HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<List<InterviewRoom>> getRooms() {
        List<InterviewRoom> interviewRooms = registrationService.getAllRooms();
        return new ResponseEntity<>(interviewRooms,HttpStatus.OK);
    }

}
