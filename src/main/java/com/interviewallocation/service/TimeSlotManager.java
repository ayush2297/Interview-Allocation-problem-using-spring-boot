package com.interviewallocation.service;

import com.interviewallocation.model.InterviewTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:scheduler.properties")
public class TimeSlotManager {

    @Value("${schedule.day.start.time}")
    private String slotStartsAt;
    @Value("${schedule.day.end.time}")
    private String dayEndsAt;
    @Value("${schedule.break.start.time}")
    private String breakStartTime;
    @Value("${schedule.break.end.time}")
    private String breakEndTime;
    @Value("${schedule.interview.duration}")
    private String interviewDuration;

    public TimeSlotManager() {
    }

    public List<InterviewTime> getSlots() {
        List<InterviewTime> slots = new ArrayList<>();
        InterviewTime interval = new InterviewTime(getLocalTime(slotStartsAt), getEndTimeBasedOnStartTime(getLocalTime(slotStartsAt)));
        while (!interval.getStartTime().isAfter(getLocalTime(dayEndsAt).minusMinutes(1))) {
            slots.add(interval);
            interval = updateInterviewTime(interval);
        }
        return slots;
    }

    public LocalTime getLocalTime(String time) {
        return LocalTime.parse(time);
    }

    private LocalTime getEndTimeBasedOnStartTime(LocalTime slotStartsAt) {
        return slotStartsAt.plusHours(Integer.parseInt(interviewDuration));
    }

    private InterviewTime updateInterviewTime(InterviewTime interviewSlot) {
        LocalTime newStartTime = interviewSlot.getEndTime();
        LocalTime newEndTime = getEndTimeBasedOnStartTime(newStartTime);
        if (new InterviewTime(newStartTime,newEndTime).overlaps(new InterviewTime(LocalTime.parse(breakStartTime),LocalTime.parse(breakEndTime)))) {
            newStartTime = getLocalTime(breakEndTime);
            newEndTime = getEndTimeBasedOnStartTime(newStartTime);
        }
        if (newStartTime.isAfter(getLocalTime(dayEndsAt).minusMinutes(1)) || newEndTime.isAfter(getLocalTime(dayEndsAt)))
            return new InterviewTime(getLocalTime(dayEndsAt), getLocalTime(dayEndsAt));
        return new InterviewTime(newStartTime, newEndTime);
    }
}
