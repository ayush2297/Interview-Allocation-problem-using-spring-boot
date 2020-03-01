package com.interviewallocation.service;

import com.interviewallocation.dto.RoomDto;
import com.interviewallocation.exception.InterviewRoomException;
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


    private InterviewRoomRepository roomRepository;


    private ModelMapper mapper;

    @Autowired
    public RoomRegistrationService(InterviewRoomRepository roomRepository, ModelMapper mapper) {
        this.roomRepository = roomRepository;
        this.mapper = mapper;
    }

    public List<Long> addInterviewRooms(List<RoomDto> roomDtos) {
        List<Long> roomIds = new ArrayList<>();
        for (RoomDto roomDto : roomDtos) {
            InterviewRoom interviewRoom = mapper.map(roomDto, InterviewRoom.class);
            InterviewRoom newRoom = roomRepository.save(interviewRoom);
            roomIds.add(newRoom.getId());
        }
        return roomIds;
    }

    public List<InterviewRoom> getAllRooms() {
        List<InterviewRoom> roomsList = roomRepository.findAll();
        if (roomsList.isEmpty())
            throw new InterviewRoomException("no records found!!",HttpStatus.NO_CONTENT);
        return roomsList;
    }
}
