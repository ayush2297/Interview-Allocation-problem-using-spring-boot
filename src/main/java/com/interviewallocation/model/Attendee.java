package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "attendees")
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    private int noOfInterviews;

    public Attendee() {
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id='" + id + '\'' +
                ", noOfInterviews=" + noOfInterviews +
                '}';
    }
}
