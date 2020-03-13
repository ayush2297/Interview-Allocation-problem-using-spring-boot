package com.interviewallocation.controller;

import com.interviewallocation.dto.OutputDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.model.Interview;
import com.interviewallocation.service.InterviewSchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchedulerControllerTest {

    private MockMvc mvc;

    @Mock
    private InterviewSchedulerService service;

    @InjectMocks
    private SchedulerController controller;

    List<Interview> interviews;
    private ArrayList<Attendee> pendingAttendees;
    private OutputDto output;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
        Attendee attendee1 = new Attendee();
        Attendee attendee2 = new Attendee();
        Interview interview1 = new Interview();
        Interview interview2 = new Interview();
        interviews = new ArrayList<>();
        pendingAttendees = new ArrayList<>();
        interviews.add(interview1);
        interviews.add(interview2);
        pendingAttendees.add(attendee1);
        pendingAttendees.add(attendee2);
        this.output = new OutputDto(interviews,pendingAttendees);
    }


    @Test
    void shouldScheduleAndReturnAllInterviews() throws Exception {
        when(service.getAllInterviews()).thenReturn(output);
        mvc.perform(MockMvcRequestBuilders
                .get("/scheduler/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interviews.length()").value(2))
                .andExpect(jsonPath("$.interviews").isNotEmpty())
                .andExpect(jsonPath("$.interviews").isNotEmpty())
                .andExpect(jsonPath("$.pendingAttendees.length()").value(2));
    }

}
