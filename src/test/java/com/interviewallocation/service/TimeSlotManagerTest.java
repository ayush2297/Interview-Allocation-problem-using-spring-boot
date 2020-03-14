package com.interviewallocation.service;

import com.interviewallocation.model.InterviewTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Annotation;
import java.time.LocalTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@TestPropertySource(locations = "/Users/ayush.saraf/IdeaProjects/Interview-Allocation-Problem/src/test/resources/scheduler-test.properties")
public class TimeSlotManagerTest {

    @InjectMocks
    private TimeSlotManager slotManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHavePropertySourceAnnotation() {
        Annotation[] annotations = slotManager.getClass().getAnnotations();
        assertTrue(asList(annotations).stream().anyMatch(annotation -> annotation.annotationType().equals(PropertySource.class)));
    }

    @Test
    @Ignore
    public void shouldReturnAvailableSlotsBasedOnProperties() {
        List<InterviewTime> slots = slotManager.getSlots();
        assertEquals(8,slots.size());
    }

    @Test
    public void givenTimeInStringShouldReturnLocalTime() {
        LocalTime time = slotManager.getLocalTime("13:00:00");
        assertEquals(LocalTime.of(13,00,00),time);
    }
}