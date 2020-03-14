package com.interviewallocation.service;

import com.interviewallocation.dto.OutputDto;
import com.interviewallocation.exception.InterviewSchedulerException;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.model.InterviewTime;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.repository.AttendeeRepository;
import com.interviewallocation.repository.InterviewRepository;
import com.interviewallocation.repository.InterviewRoomRepository;
import com.interviewallocation.repository.InterviewerRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class InterviewSchedulerServiceTest {

    @Mock
    private AttendeeRepository attendeeRepository;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Mock
    private InterviewRoomRepository roomRepository;

    @Mock
    private TimeSlotManager slotManager;

    @Mock
    private InterviewRepository interviewRepository;

    @InjectMocks
    private InterviewSchedulerService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<Attendee> attendees;
    private List<Interviewer> interviewers;
    private List<InterviewRoom> rooms;
    private List<InterviewTime> slots;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        attendees = new ArrayList<>();
        interviewers = new ArrayList<>();
        rooms = new ArrayList<>();
        slots = new ArrayList<>();
    }

    @Test
    public void shouldScheduleInterviewsUsingDataFromAllReposAndReturnOutputDto() {
        Attendee attendee = new Attendee(1L,"abc",1);
        attendees.add(attendee);
        Interviewer interviewer = new Interviewer(1L,"p1",LocalTime.of(13,00),LocalTime.of(14,00),7);
        interviewers.add(interviewer);
        InterviewRoom room = new InterviewRoom();
        rooms.add(room);
        InterviewTime interviewTime = new InterviewTime(LocalTime.of(9, 00), LocalTime.of(10, 00));
        slots.add(interviewTime);
        when(attendeeRepository.findAll()).thenReturn(attendees);
        when(interviewerRepository.findAll()).thenReturn(interviewers);
        when(roomRepository.findAll()).thenReturn(rooms);
        when(slotManager.getSlots()).thenReturn(slots);

        OutputDto allInterviews = service.getAllInterviews();

        assertEquals(1,allInterviews.getInterviews().size());
        assertEquals(0,allInterviews.getPendingAttendees().size());
    }

    @Test
    public void shouldThrowExceptionIfNoAttendeesArePresent() {
        expectedException.expect(InterviewSchedulerException.class);
        expectedException.expectMessage("unable to get data from db");
        when(attendeeRepository.findAll()).thenReturn(attendees);

        OutputDto allInterviews = service.getAllInterviews();
    }

    @Test
    public void shouldThrowExceptionIfNoInterviewersArePresent() {
        expectedException.expect(InterviewSchedulerException.class);
        expectedException.expectMessage("unable to get data from db");
        when(interviewerRepository.findAll()).thenReturn(interviewers);

        OutputDto allInterviews = service.getAllInterviews();
    }

    @Test
    public void shouldThrowExceptionIfNoInterviewRoomsArePresent() {
        expectedException.expect(InterviewSchedulerException.class);
        expectedException.expectMessage("unable to get data from db");
        when(roomRepository.findAll()).thenReturn(rooms);

        OutputDto allInterviews = service.getAllInterviews();
    }

    @Test
    public void ifInterviewersAreNotAvailableThenShouldConsiderNextSlot() {
        Attendee attendee = new Attendee(1L,"abc",1);
        attendees.add(attendee);
        Interviewer interviewer = new Interviewer(1L,"p1",LocalTime.of(13,00),LocalTime.of(14,00),7);
        interviewers.add(interviewer);
        InterviewRoom room = new InterviewRoom();
        rooms.add(room);
        InterviewTime interviewTime = new InterviewTime(LocalTime.of(13, 00), LocalTime.of(14, 00));
        slots.add(interviewTime);
        when(attendeeRepository.findAll()).thenReturn(attendees);
        when(interviewerRepository.findAll()).thenReturn(interviewers);
        when(roomRepository.findAll()).thenReturn(rooms);
        when(slotManager.getSlots()).thenReturn(slots);

        OutputDto allInterviews = service.getAllInterviews();

        assertEquals(0,allInterviews.getInterviews().size());
        assertEquals(1,allInterviews.getPendingAttendees().size());
    }

    @Test
    public void givenInterviewersAreAvailableButRoomsAreNotAvailableThenShouldConsiderNextSlot() {
        Attendee attendee1 = new Attendee(1L,"abc",1);
        Attendee attendee2 = new Attendee(2L,"def",1);
        attendees.add(attendee1);
        attendees.add(attendee2);
        Interviewer interviewer1 = new Interviewer(1L,"p1",LocalTime.of(13,00),LocalTime.of(14,00),7);
        Interviewer interviewer2 = new Interviewer(2L,"p2",LocalTime.of(15,00),LocalTime.of(16,00),7);
        interviewers.add(interviewer1);
        interviewers.add(interviewer2);
        InterviewRoom room = new InterviewRoom();
        rooms.add(room);
        InterviewTime interviewTime = new InterviewTime(LocalTime.of(9, 00), LocalTime.of(10, 00));
        slots.add(interviewTime);
        when(attendeeRepository.findAll()).thenReturn(attendees);
        when(interviewerRepository.findAll()).thenReturn(interviewers);
        when(roomRepository.findAll()).thenReturn(rooms);
        when(slotManager.getSlots()).thenReturn(slots);

        OutputDto allInterviews = service.getAllInterviews();

        assertEquals(1,allInterviews.getInterviews().size());
        assertEquals(1,allInterviews.getPendingAttendees().size());
    }


    @Test
    public void givenRoomsAreAvailableButInterviewersAreNotAvailableThenShouldConsiderNextSlot() {
        Attendee attendee1 = new Attendee(1L,"abc",1);
        Attendee attendee2 = new Attendee(2L,"def",1);
        attendees.add(attendee1);
        attendees.add(attendee2);
        Interviewer interviewer1 = new Interviewer(1L,"p1",LocalTime.of(13,00),LocalTime.of(14,00),7);
        interviewers.add(interviewer1);
        InterviewRoom room1 = new InterviewRoom();
        InterviewRoom room2 = new InterviewRoom();
        rooms.add(room1);
        rooms.add(room2);
        InterviewTime interviewTime = new InterviewTime(LocalTime.of(9, 00), LocalTime.of(10, 00));
        slots.add(interviewTime);
        when(attendeeRepository.findAll()).thenReturn(attendees);
        when(interviewerRepository.findAll()).thenReturn(interviewers);
        when(roomRepository.findAll()).thenReturn(rooms);
        when(slotManager.getSlots()).thenReturn(slots);

        OutputDto allInterviews = service.getAllInterviews();

        assertEquals(1,allInterviews.getInterviews().size());
        assertEquals(1,allInterviews.getPendingAttendees().size());
    }
}