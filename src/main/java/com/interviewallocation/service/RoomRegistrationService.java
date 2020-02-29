package com.interviewallocation.service;

import com.interviewallocation.dto.AttendeeDto;
import com.interviewallocation.dto.RoomDto;
import com.interviewallocation.exception.InterviewRoomException;
import com.interviewallocation.model.Attendee;
import com.interviewallocation.model.InterviewRoom;
import com.interviewallocation.repository.InterviewRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomRegistrationService {

    @Autowired
    private InterviewRoomRepository roomRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Long> addInterviewRooms(List<RoomDto> roomDtos) {
        List<Long> roomIds = new ArrayList<>();
        for (RoomDto roomDto : roomDtos) {
            InterviewRoom interviewRoom = mapper.map(roomDto, InterviewRoom.class);
            InterviewRoom newRoom = roomRepository.save(interviewRoom);
            roomIds.add(newRoom.getId());
        }
        return roomIds;
    }
}