package com.interviewallocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.service.AttendeeRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class AttendeeControllerTest {

    private MockMvc mvc;

    @Mock
    AttendeeRegistrationService attendeeService;

    @InjectMocks
    AttendeeController controller;

    private ArrayList<AttendeeDto> attendees;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
        attendees = new ArrayList<AttendeeDto>();
        AttendeeDto attendeeDto1 = new AttendeeDto();
        AttendeeDto attendeeDto2 = new AttendeeDto();
        attendees.add(attendeeDto1);
        attendees.add(attendeeDto2);
    }

    @Test
    public void shouldAddAllAttendeesAndReturnTheirIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        when(attendeeService.addAttendees(attendees)).thenReturn(ids);
        try {
            ResultActions resultActions = mvc.perform(post("/attendee/add").content(asJsonString(attendees)).contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(status().isOk());
            resultActions.andExpect(jsonPath("$..length()", is(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(attendeeService, times(1)).addAttendees(attendees);
        verifyNoMoreInteractions(attendeeService);
    }

    @org.junit.jupiter.api.Test
    public void getAttendees() throws Exception {
        List<Attendee> attendees = new ArrayList<>();
        Attendee attendee = new Attendee();
        attendees.add(attendee);
        when(attendeeService.getAllAttendees()).thenReturn(attendees);
        mvc.perform(MockMvcRequestBuilders
                .get("/attendee/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
