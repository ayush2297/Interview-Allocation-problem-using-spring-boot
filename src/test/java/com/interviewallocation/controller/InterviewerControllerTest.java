package com.interviewallocation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewallocation.dto.InterviewerDto;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.service.InterviewerRegistrationService;
import org.junit.Before;
import org.junit.Test;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.interviewallocation.util.TestHelper.asJsonString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class InterviewerControllerTest {

    private MockMvc mvc;

    @Mock
    InterviewerRegistrationService registrationService;

    @InjectMocks
    InterviewerController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldAddAllAttendeesAndReturnTheirIds() throws JsonProcessingException {
        List<InterviewerDto> interviewerDtos = new ArrayList<>();
        interviewerDtos.add(new InterviewerDto());
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        String json = asJsonString(interviewerDtos);
        when(registrationService.addAllInterviewers(anyList())).thenReturn(ids);
        try {
            mvc.perform(post("/interviewer/add")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.size()").value(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(registrationService).addAllInterviewers(anyList());
        verifyNoMoreInteractions(registrationService);
    }

    @Test
    public void shouldGetAllInterviewers() throws Exception {
        List<Interviewer> interviewers = new ArrayList<>();
        Interviewer interviewer = new Interviewer();
        interviewers.add(interviewer);
        when(registrationService.getAllInterviewers()).thenReturn(interviewers);
        mvc.perform(MockMvcRequestBuilders
                .get("/interviewer/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }

    public List<InterviewerDto> asAttendeeDto(String jsonString) throws JsonProcessingException {
        InterviewerDto[] interviewerDtos = new ObjectMapper().readValue(jsonString, InterviewerDto[].class);
        return Arrays.stream(interviewerDtos).collect(Collectors.toList());
    }
}