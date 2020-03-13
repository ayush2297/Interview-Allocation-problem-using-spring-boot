package com.interviewallocation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.service.AttendeeRegistrationService;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.interviewallocation.util.TestHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttendeeControllerTest {

    private MockMvc mvc;

    @Mock
    AttendeeRegistrationService attendeeService;

    @InjectMocks
    AttendeeController controller;

    private List<AttendeeDto> attendees;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
        attendees = new ArrayList<>();
        AttendeeDto attendeeDto1 = new AttendeeDto("abc", 2);
        AttendeeDto attendeeDto2 = new AttendeeDto("pqr", 1);
        attendees.add(attendeeDto1);
        attendees.add(attendeeDto2);
    }

    @Test
    public void shouldAddAllAttendeesAndReturnTheirIds() throws JsonProcessingException {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        String json = asJsonString(attendees);
        when(attendeeService.addAttendees(anyList())).thenReturn(ids);
        try {
            mvc.perform(post("/attendee/add")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.size()").value(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(attendeeService).addAttendees(anyList());
        verifyNoMoreInteractions(attendeeService);
    }

    @Test
    public void shouldCallGetAttendees() throws Exception {
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

    public List<AttendeeDto> asAttendeeDto(String jsonString) throws JsonProcessingException {
        AttendeeDto[] attendeeDtosArray = new ObjectMapper().readValue(jsonString, AttendeeDto[].class);
        return Arrays.stream(attendeeDtosArray).collect(Collectors.toList());
    }
}
