package com.interviewallocation.service;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.repository.AttendeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendeeRegistrationService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Long> addAttendees(List<AttendeeDto> attendeeList) {
        List<Long> attendeeIds = new ArrayList<>();
        for (AttendeeDto attendeeDto : attendeeList) {
            Attendee attendee = mapper.map(attendeeDto, Attendee.class);
            Attendee newAttendee = attendeeRepository.save(attendee);
            attendeeIds.add(newAttendee.getId());
        }
        return attendeeIds;
    }
}
