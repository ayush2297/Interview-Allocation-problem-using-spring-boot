package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity(name = "interview_schedule")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @Getter @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "interviewer_id", nullable = false)
    private Interviewer interviewer;

    @Getter @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "interviewRoom_id", nullable = false)
    private InterviewRoom interviewRoom;

    @Getter @Setter
    private LocalTime startTime;

    @Getter @Setter
    private LocalTime endTime;

    public Interview() {
    }

    public Interview(Attendee attendee, Interviewer selectedInterviewer, InterviewRoom selectedRoom, LocalTime startTime, LocalTime endTime) {
        this.attendee = attendee;
        interviewer = selectedInterviewer;
        interviewRoom = selectedRoom;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "\nInterview{" +
                "\n\tid=" + id +
                ",\n\tattendee=" + attendee.getName() +
                ",\n\tinterviewer=" + interviewer.getName() +
                ",\n\tinterviewRoom=" + interviewRoom.getName() +
                ",\n\tstartTime=" + startTime.getHour() +
                ",\n\tendTime=" + endTime.getHour() +
                "\n}";
    }
}
