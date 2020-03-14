package com.interviewallocation.service;

import com.interviewallocation.dto.RoomDto;
import com.interviewallocation.exception.InterviewRoomException;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.repository.InterviewRoomRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoomRegistrationServiceTest {

    @Mock
    private InterviewRoomRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RoomRegistrationService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddAttendeesAndReturnTheirIds() {
        List<RoomDto> roomDtos = new ArrayList<>();
        RoomDto roomDto1 = new RoomDto();
        RoomDto roomDto2 = new RoomDto();
        InterviewRoom room1 = new InterviewRoom();
        InterviewRoom room2 = new InterviewRoom();
        room1.setId(1L);
        room2.setId(2L);
        roomDtos.add(roomDto1);
        roomDtos.add(roomDto2);

        when(mapper.map(roomDto1, InterviewRoom.class)).thenReturn(room1);
        when(mapper.map(roomDto2, InterviewRoom.class)).thenReturn(room2);
        when(repository.save(room1)).thenReturn(room1);
        when(repository.save(room2)).thenReturn(room2);

        List<Long> ids = service.addInterviewRooms(roomDtos);
        assertEquals(2, ids.size());

        verify(repository).save(room1);
        verify(repository).save(room2);
    }

    @Test
    public void shouldGetAllAttendees() {
        List<InterviewRoom> attendees = new ArrayList<>();
        InterviewRoom attendee1 = new InterviewRoom();
        InterviewRoom attendee2 = new InterviewRoom();
        attendees.add(attendee1);
        attendees.add(attendee2);

        when(repository.findAll()).thenReturn(attendees);

        List<InterviewRoom> allAttendees = service.getAllRooms();

        assertEquals(2, allAttendees.size());
    }

    @Test
    public void shouldThrowExceptionIfNoAttendeesPresent() {
        expectedException.expect(InterviewRoomException.class);
        expectedException.expectMessage("no records found!!");

        when(repository.findAll()).thenReturn(new ArrayList<>());

        service.getAllRooms();
    }
}