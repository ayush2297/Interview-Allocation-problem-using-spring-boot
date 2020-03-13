package com.interviewallocation.controller;

import com.interviewallocation.dto.RoomDto;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.service.RoomRegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.interviewallocation.util.TestHelper.asJsonString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InterviewRoomControllerTest {

    private MockMvc mvc;

    @Mock
    RoomRegistrationService registrationService;

    @InjectMocks
    InterviewRoomController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldAddAllInterviewRooms() {
        List<RoomDto> interviewerDtos = new ArrayList<>();
        interviewerDtos.add(new RoomDto());
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        String json = asJsonString(interviewerDtos);
        when(registrationService.addInterviewRooms(anyList())).thenReturn(ids);
        try {
            mvc.perform(post("/interview-room/add")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.size()").value(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        verify(registrationService).addInterviewRooms(anyList());
        verifyNoMoreInteractions(registrationService);
    }

    @Test
    public void shouldGetAllInterviewRooms() throws Exception {
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