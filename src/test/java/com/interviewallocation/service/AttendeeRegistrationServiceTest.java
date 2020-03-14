package com.interviewallocation.service;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.exception.AttendeesNotFoundException;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.repository.AttendeeRepository;
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

public class AttendeeRegistrationServiceTest {

    @Mock
    private AttendeeRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AttendeeRegistrationService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddAttendeesAndReturnTheirIds() {
        List<AttendeeDto> attendeeDtos = new ArrayList<>();
        AttendeeDto attendeeDto1 = new AttendeeDto();
        AttendeeDto attendeeDto2 = new AttendeeDto();
        Attendee attendee1 = new Attendee();
        Attendee attendee2 = new Attendee();
        attendee1.setId(1L);
        attendee2.setId(2L);
        attendeeDtos.add(attendeeDto1);
        attendeeDtos.add(attendeeDto2);

        when(mapper.map(attendeeDto1, Attendee.class)).thenReturn(attendee1);
        when(mapper.map(attendeeDto2, Attendee.class)).thenReturn(attendee2);

        when(repository.save(attendee1)).thenReturn(attendee1);
        when(repository.save(attendee2)).thenReturn(attendee2);

        List<Long> ids = service.addAttendees(attendeeDtos);
        assertEquals(2, ids.size());

        attendee1.setId(1L);
        verify(repository).save(attendee1);
        verify(repository).save(attendee2);
    }

    @Test
    public void shouldGetAllAttendees() {
        List<Attendee> attendees = new ArrayList<>();
        Attendee attendee1 = new Attendee();
        Attendee attendee2 = new Attendee();
        attendees.add(attendee1);
        attendees.add(attendee2);

        when(repository.findAll()).thenReturn(attendees);

        List<Attendee> allAttendees = service.getAllAttendees();

        assertEquals(2, allAttendees.size());
    }

    @Test
    public void shouldThrowExceptionIfDbHasNoRecords() {
        expectedException.expect(AttendeesNotFoundException.class);
        expectedException.expectMessage("no records found!!");
        when(repository.findAll()).thenReturn(new ArrayList<>());

        service.getAllAttendees();

    }
}