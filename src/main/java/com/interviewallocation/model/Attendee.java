package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "attendees")
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(unique = true,nullable = false)
    private String name;

    @Getter
    @Setter
    @NotNull
    private int noOfInterviews;

    public Attendee() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendee attendee = (Attendee) o;
        return noOfInterviews == attendee.noOfInterviews &&
                Objects.equals(id, attendee.id) &&
                Objects.equals(name, attendee.name);
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", noOfInterviews=" + noOfInterviews +
                '}';
    }
}
