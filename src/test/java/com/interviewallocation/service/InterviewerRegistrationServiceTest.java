package com.interviewallocation.service;

import com.interviewallocation.dto.InterviewerDto;
import com.interviewallocation.exception.InterviewerException;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.repository.InterviewerRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InterviewerRegistrationServiceTest {

    @Mock
    private InterviewerRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private InterviewerRegistrationService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddAllInterviewersGivenTheirBreakTimesAreValid() {
        List<InterviewerDto> interviewerDtos = new ArrayList<>();
        InterviewerDto interviewerDto1 = new InterviewerDto();
        InterviewerDto interviewerDto2 = new InterviewerDto();
        LocalTime breakStart1 = LocalTime.of(13, 00);
        LocalTime breakEnd1 = LocalTime.of(14, 00);
        LocalTime breakStart2 = LocalTime.of(15, 00);
        LocalTime breakEnd2 = LocalTime.of(16, 00);
        interviewerDto1.setBreakStartTime(breakStart1);
        interviewerDto1.setBreakEndTime(breakEnd1);
        interviewerDto2.setBreakStartTime(breakStart2);
        interviewerDto2.setBreakEndTime(breakEnd2);
        Interviewer interviewer1 = new Interviewer();
        Interviewer interviewer2 = new Interviewer();
        interviewer1.setId(1L);
        interviewer2.setId(2L);
        interviewerDtos.add(interviewerDto1);
        interviewerDtos.add(interviewerDto2);

        when(mapper.map(interviewerDto1, Interviewer.class)).thenReturn(interviewer1);
        when(mapper.map(interviewerDto2, Interviewer.class)).thenReturn(interviewer2);
        when(repository.save(interviewer1)).thenReturn(interviewer1);
        when(repository.save(interviewer2)).thenReturn(interviewer2);

        List<Long> ids = service.addAllInterviewers(interviewerDtos);
        assertEquals(2, ids.size());

        verify(repository).save(interviewer1);
        verify(repository).save(interviewer2);
    }

    @Test
    public void shouldThrowExceptionIfGivenInterviewersBreakTimesAreInvalid() {
        expectedException.expect(InterviewerException.class);
        expectedException.expectMessage("break end time cannot be before break end time");
        List<InterviewerDto> interviewerDtos = new ArrayList<>();
        InterviewerDto interviewerDto1 = new InterviewerDto();
        interviewerDto1.setBreakStartTime(LocalTime.of(13, 00));
        interviewerDto1.setBreakEndTime(LocalTime.of(12, 00));
        interviewerDtos.add(interviewerDto1);

        List<Long> ids = service.addAllInterviewers(interviewerDtos);
        verify(mapper, never()).map(interviewerDto1, Interviewer.class);
        verify(repository, never()).save(any());
    }

    @Test
    public void shouldSetBreakTimeAsMidnightIfGivenInterviewersBreakTimesAreZero() {
        List<InterviewerDto> interviewerDtos = new ArrayList<>();
        InterviewerDto interviewerDto1 = new InterviewerDto();
        interviewerDto1.setBreakStartTime(LocalTime.of(00, 00));
        interviewerDto1.setBreakEndTime(LocalTime.of(00, 00));
        Interviewer interviewer1 = new Interviewer();
        interviewer1.setId(1L);
        interviewerDtos.add(interviewerDto1);

        when(mapper.map(interviewerDto1, Interviewer.class)).thenReturn(interviewer1);
        when(repository.save(interviewer1)).thenReturn(interviewer1);

        List<Long> ids = service.addAllInterviewers(interviewerDtos);
        assertEquals(1, ids.size());

        verify(repository).save(interviewer1);
    }

    @Test
    public void shouldGetAllInterviewers() {
        List<Interviewer> Interviewers = new ArrayList<>();
        Interviewer interviewer1 = new Interviewer();
        Interviewer interviewer2 = new Interviewer();
        Interviewers.add(interviewer1);
        Interviewers.add(interviewer2);

        when(repository.findAll()).thenReturn(Interviewers);

        List<Interviewer> allInterviewers = service.getAllInterviewers();

        assertEquals(2, allInterviewers.size());
    }

    @Test
    public void shouldThrowExceptionIfNoInterviewersPresent() {
        expectedException.expect(InterviewerException.class);
        expectedException.expectMessage("no records found!!");
        List<Interviewer> interviewers = new ArrayList<>();

        when(repository.findAll()).thenReturn(interviewers);

        List<Interviewer> allInterviewers = service.getAllInterviewers();
    }
}