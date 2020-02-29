package com.interviewallocation.service;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.exception.AttendeesNotFoundException;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.repository.AttendeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendeeRegistrationService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Long> addAttendees(@NotEmpty(message = "list cannot be empty") List<@Valid AttendeeDto> attendeesDto) {
        List<Long> attendeeIds = new ArrayList<>();
        for (AttendeeDto attendeeDto : attendeesDto) {
            Attendee attendee = mapper.map(attendeeDto, Attendee.class);
            Attendee newAttendee = attendeeRepository.save(attendee);
            attendeeIds.add(newAttendee.getId());
        }
        return attendeeIds;
    }

    public List<Attendee> getAllAttendees() {
        List<Attendee> attendees = attendeeRepository.findAll();
        if (attendees.isEmpty())
            throw new AttendeesNotFoundException("no records found!!");
        return attendees;
    }
}
