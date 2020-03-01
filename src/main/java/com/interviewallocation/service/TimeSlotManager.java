package com.interviewallocation.service;

import com.interviewallocation.model.InterviewTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotManager {

    private LocalTime slotStartsAt = LocalTime.parse("09:00:00");
    private LocalTime dayEndsAt = LocalTime.parse("17:00:00");
    private LocalTime breakStartTime = LocalTime.parse("14:00:00");
    private LocalTime breakEndTime = LocalTime.parse("15:00:00");
    private int interviewDuration = 1;

    public TimeSlotManager() {
    }

    public List<InterviewTime> getSlots() {
        List<InterviewTime> slots = new ArrayList<>();
        InterviewTime interval = new InterviewTime(slotStartsAt, getEndTimeBasedOnStartTime(slotStartsAt));
        while (!interval.getStartTime().isAfter(dayEndsAt.minusMinutes(1))) {
            slots.add(interval);
            interval = updateInterviewTime(interval);
        }
        return slots;
    }

    private LocalTime getEndTimeBasedOnStartTime(LocalTime slotStartsAt) {
        return slotStartsAt.plusHours(interviewDuration);
    }

    private InterviewTime updateInterviewTime(InterviewTime interviewSlot) {
        LocalTime newStartTime = interviewSlot.getEndTime();
        LocalTime newEndTime = getEndTimeBasedOnStartTime(newStartTime);
        if (new InterviewTime(newStartTime,newEndTime).overlaps(new InterviewTime(breakStartTime,breakEndTime))) {
            newStartTime = breakEndTime;
            newEndTime = getEndTimeBasedOnStartTime(newStartTime);
        }
        if (newStartTime.isAfter(dayEndsAt.minusMinutes(1)) || newEndTime.isAfter(dayEndsAt))
            return new InterviewTime(dayEndsAt, dayEndsAt);
        return new InterviewTime(newStartTime, newEndTime);
    }
}
