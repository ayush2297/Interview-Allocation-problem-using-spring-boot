package com.interviewallocation.service;

import com.interviewallocation.dto.InterviewerDto;
import com.interviewallocation.exception.InterviewRoomException;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.model.Interviewer;
import com.interviewallocation.repository.InterviewerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewerRegistrationService {

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Long> addAllInterviewers(List<InterviewerDto> interviewers) {
        List<Long> interviewerIds = new ArrayList<>();
        for (InterviewerDto interviewerDto : interviewers) {
            Interviewer interviewer = mapper.map(interviewerDto, Interviewer.class);
            Interviewer newInterviewer = interviewerRepository.save(interviewer);
            interviewerIds.add(newInterviewer.getId());
        }
        return interviewerIds;
    }

    public List<Interviewer> getAllInterviewers() {
        List<Interviewer> interviewerList = interviewerRepository.findAll();
        if (interviewerList.isEmpty())
            throw new InterviewRoomException("no records found!!", HttpStatus.NO_CONTENT);
        return interviewerList;
    }
}
