package com.interviewallocation.model;

import lombok.Getter;
import java.time.LocalTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class InterviewTime {

    @Getter
    private LocalTime startTime;

    @Getter
    private LocalTime endTime;

    public InterviewTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(InterviewTime other) {
        requireNonNull(other, "other time must not be null");
        if (this.equals(other))
            return true;
        return isBetween(other.startTime, this.startTime, this.endTime)
                || isBetween(other.endTime, this.startTime, this.endTime)
                || isBetween(this.startTime, other.startTime, other.endTime)
                || isBetween(this.endTime, other.startTime, other.endTime);
    }

    private static boolean isBetween(LocalTime t, LocalTime from, LocalTime to) {
        if (from.isBefore(to)) {
            return from.isBefore(t) && t.isBefore(to);
        } else {
            return from.isBefore(t) || t.isBefore(to);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewTime that = (InterviewTime) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public String toString() {
        return "InterviewTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
