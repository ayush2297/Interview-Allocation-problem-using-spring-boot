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
            if (interviewerDto.getBreakStartTime().equals("0") || interviewerDto.getBreakEndTime().equals("0"))
                setBreakTime(interviewer, "0", "0");
            else {
                setBreakTime(interviewer, interviewerDto.getBreakStartTime(), interviewerDto.getBreakEndTime());
            }
            Interviewer newInterviewer = interviewerRepository.save(interviewer);
            interviewerIds.add(newInterviewer.getId());
        }
        return interviewerIds;
    }

    private void validateBreakTime(List<InterviewerDto> interviewers) {
        for (InterviewerDto interviewerDto : interviewers) {
            if (Integer.parseInt(interviewerDto.getBreakStartTime()) < Integer.parseInt(interviewerDto.getBreakEndTime()))
                throw new InterviewerException("break end time cannot be before break end time at : "+interviewerDto);
        }
    }

    public List<Interviewer> getAllInterviewers() {
        List<Interviewer> interviewerList = interviewerRepository.findAll();
        if (interviewerList.isEmpty())
            throw new InterviewRoomException("no records found!!", HttpStatus.NO_CONTENT);
        return interviewerList;
    }

    private void setBreakTime(Interviewer interviewer, String breakStartTime, String breakEndTime) {
        interviewer.setBreakStart(breakStartTime);
        interviewer.setBreakEnd(breakEndTime);
        long availableHours = 8 -
                (LocalTime.parse(breakStartTime + ":00:00").until(LocalTime.parse(breakEndTime + ":00:00"), ChronoUnit.HOURS));
        interviewer.setNoOfHoursAvailable(availableHours);
    }
}
