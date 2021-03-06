package com.interviewallocation.dto;

import com.interviewallocation.model.Attendee;
import com.interviewallocation.model.Interview;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class OutputDto {

    @Getter
    private List<Interview> interviews;

    @Getter
    private List<Attendee> pendingAttendees;

    public OutputDto(List<Interview> interviews, List<Attendee> pendingAttendees) {
        this.interviews = interviews;
        this.pendingAttendees = pendingAttendees;
    }

    @Override
    public String toString() {
        return "OutputDto{" +
                "interviews=" + interviews +
                ", pendingAttendees=" + pendingAttendees +
                '}';
    }
}
