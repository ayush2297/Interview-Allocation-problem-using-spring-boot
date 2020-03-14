package com.interviewallocation.service;

import com.interviewallocation.dto.InterviewerDto;
import com.interviewallocation.exception.InterviewRoomException;
import com.interviewallocation.exception.InterviewerException;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.repository.InterviewerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewerRegistrationService {

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Long> addAllInterviewers(List<InterviewerDto> interviewers) {
        validateBreakTime(interviewers);
        List<Long> interviewerIds = new ArrayList<>();
        for (InterviewerDto interviewerDto : interviewers) {
            Interviewer interviewer = mapper.map(interviewerDto, Interviewer.class);
            if (interviewerDto.getBreakStartTime().getHour() == 0 || interviewerDto.getBreakEndTime().getHour() == 0)
                setBreakTime(interviewer, LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusMinutes(1));
            else {
                setBreakTime(interviewer, interviewerDto.getBreakStartTime(), interviewerDto.getBreakEndTime());
            }
            Interviewer newInterviewer = interviewerRepository.save(interviewer);
            interviewerIds.add(newInterviewer.getId());
        }
        return interviewerIds;
    }

    public List<Interviewer> getAllInterviewers() {
        List<Interviewer> interviewerList = interviewerRepository.findAll();
        if (interviewerList.isEmpty())
            throw new InterviewerException("no records found!!", HttpStatus.NO_CONTENT);
        return interviewerList;
    }

    private void validateBreakTime(List<InterviewerDto> interviewers) {
        for (InterviewerDto interviewerDto : interviewers) {
            if (interviewerDto.getBreakStartTime().isAfter(interviewerDto.getBreakEndTime()))
                throw new InterviewerException("break end time cannot be before break end time"
                        , HttpStatus.BAD_REQUEST);
        }
    }

    private void setBreakTime(Interviewer interviewer, LocalTime breakStartTime, LocalTime breakEndTime) {
        interviewer.setBreakStart(breakStartTime);
        interviewer.setBreakEnd(breakEndTime);
        long availableHours = 8 - (breakStartTime.until(breakEndTime, ChronoUnit.HOURS));
        interviewer.setNoOfHoursAvailable(availableHours);
    }
}
