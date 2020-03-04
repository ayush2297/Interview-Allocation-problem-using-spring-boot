package com.interviewallocation.controller;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.dto.InterviewerDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.service.AttendeeRegistrationService;
import com.interviewallocation.service.InterviewerRegistrationService;
import com.interviewallocation.service.RoomRegistrationService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class InterviewRoomControllerTest {

    private MockMvc mvc;

    @Mock
    RoomRegistrationService registrationService;

    @InjectMocks
    InterviewRoomController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @org.junit.jupiter.api.Test
    public void getAttendees() throws Exception {
        List<InterviewRoom> rooms = new ArrayList<>();
        InterviewRoom room = new InterviewRoom();
        rooms.add(room);
        when(registrationService.getAllRooms()).thenReturn(rooms);
        mvc.perform(MockMvcRequestBuilders
                .get("/interview-room/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }
}