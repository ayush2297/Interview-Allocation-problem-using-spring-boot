package com.interviewallocation.service;

import com.interviewallocation.model.InterviewTime;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class InterviewTimeTest {

    @Test
    public void checkOverlap() {
        InterviewTime slot1 = getInterval("08:00:00","10:00:00");
        InterviewTime slot2 = getInterval("09:00:00","10:00:00");
        boolean overlaps = slot1.overlaps(slot2);
        assertTrue(overlaps);
    }

    @Test
    public void checkIfNotOverlap() {
        InterviewTime slot1 = getInterval("08:00:00","09:00:00");
        InterviewTime slot2 = getInterval("09:00:00","10:00:00");
        boolean overlaps = slot1.overlaps(slot2);
        assertFalse(overlaps);
    }

    private InterviewTime getInterval(String start, String end) {
        return new InterviewTime(getTime(start),getTime(end));
    }

    private LocalTime getTime(CharSequence time) {
        return LocalTime.parse(time);
    }
}
