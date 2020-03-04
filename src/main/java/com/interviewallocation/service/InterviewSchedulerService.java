package com.interviewallocation.service;

import com.interviewallocation.dto.OutputDto;
import com.interviewallocation.exception.InterviewSchedulerException;
import com.interviewallocation.model.*;
import com.interviewallocation.repository.AttendeeRepository;
import com.interviewallocation.repository.InterviewRepository;
import com.interviewallocation.repository.InterviewRoomRepository;
import com.interviewallocation.repository.InterviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewSchedulerService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private InterviewRoomRepository roomRepository;

    @Autowired
    private TimeSlotManager slotManager;

    @Autowired
    private InterviewRepository interviewRepository;

    public InterviewSchedulerService() {
    }

    public OutputDto getAllInterviews() {
        List<Attendee> attendees = getData(attendeeRepository);
        List<Interviewer> interviewers = getData(interviewerRepository);
        List<InterviewRoom> rooms = getData(roomRepository);
        return scheduleInterviews(attendees, interviewers, rooms);
    }

    private <E> List<E> getData(JpaRepository<E, Long> repository) {
        List<E> data = repository.findAll();
        if (data == null)
            throw new InterviewSchedulerException("unable to get data from db", HttpStatus.NO_CONTENT);
        return data;
    }

    private OutputDto scheduleInterviews(List<Attendee> attendees, List<Interviewer> interviewers, List<InterviewRoom> rooms) {
        int roomNo = 0;
        List<Interview> interviews = new ArrayList<>();
        List<InterviewTime> availableSlots = slotManager.getSlots();
        List<Interviewer> pendingInterviewers = new ArrayList<>();
        for (InterviewTime slot : availableSlots) {
            List<Attendee> updatedAttendeeList = getPendingAttendees(attendees);
            List<Interviewer> availableInterviewers = getAllAvailableInterviewers(interviewers, slot, pendingInterviewers);
            int isRoomCountMoreThanInterviewers = rooms.size() - availableInterviewers.size();
            int interviewerNo = 0;
            while (true) {
                if (updatedAttendeeList.isEmpty() || availableInterviewers.isEmpty())
                    break;
                Attendee selectedAttendee = getAttendee(updatedAttendeeList);
                Interviewer selectedInterviewer = getNextAvailableInterviewer(availableInterviewers);
                InterviewRoom selectedRoom = rooms.get(roomNo++);
                interviewerNo = resetCounterIfAllUsed(++interviewerNo, availableInterviewers.size());
                roomNo = resetCounterIfAllUsed(roomNo, rooms.size());
                interviews.add(new Interview(selectedAttendee, selectedInterviewer, selectedRoom, slot.getStartTime(), slot.getEndTime()));
                if (shouldUpdateInterviewSlot(interviewerNo, roomNo, isRoomCountMoreThanInterviewers)) {
                    pendingInterviewers.addAll(availableInterviewers);
                    break;
                }
            }
        }
        interviews.stream().map(interview -> interviewRepository.save(interview));
        return new OutputDto(interviews, getPendingAttendees(attendees));
    }

    private Attendee getAttendee(List<Attendee> tempSortedAttendeeList) {
        Attendee attendee = tempSortedAttendeeList.get(0);
        attendee.setNoOfInterviews(attendee.getNoOfInterviews() - 1);
        tempSortedAttendeeList.remove(attendee);
        return attendee;
    }

    private List<Interviewer> getAllAvailableInterviewers(List<Interviewer> interviewers, InterviewTime slot, List<Interviewer> nextIteration) {
        List<Interviewer> availableInterviewers = new ArrayList<>(nextIteration);
        nextIteration.clear();
        for (int i = 0; i < interviewers.size(); i++) {
            Interviewer interviewer = interviewers.get(i);
            if (!availableInterviewers.contains(interviewer))
                availableInterviewers.add(interviewer);
        }
        int i = 0;
        while (i < availableInterviewers.size()) {
            Interviewer interviewer = availableInterviewers.get(i);
            if ((new InterviewTime(interviewer.getBreakStart(), interviewer.getBreakEnd()).overlaps(slot))) {
                availableInterviewers.remove(interviewer);
                nextIteration.add(interviewer);
            } else {
                i++;
            }
        }
        return availableInterviewers;
    }

    private Interviewer getNextAvailableInterviewer(List<Interviewer> interviewers) {
        Interviewer interviewer = interviewers.get(0);
        interviewers.remove(interviewer);
        return interviewer;
    }

    private List<Attendee> getPendingAttendees(List<Attendee> attendees) {
        return attendees.stream()
                .filter(attendee -> attendee.getNoOfInterviews() != 0)
                .sorted(Comparator.comparing(Attendee::getNoOfInterviews))
                .collect(Collectors.toList());
    }

    private boolean shouldUpdateInterviewSlot(int interviewerNo, int roomNo, int isRoomCountMoreThanInterviewers) {
        if (isRoomCountMoreThanInterviewers > 0) {
            return roomNo == 0;
        } else if (isRoomCountMoreThanInterviewers < 0) {
            return interviewerNo == 0;
        } else {
            return roomNo == 0;
        }
    }

    private int resetCounterIfAllUsed(int counter, int listSize) {
        if (counter >= listSize) {
            return 0;
        }
        return counter;
    }

}
