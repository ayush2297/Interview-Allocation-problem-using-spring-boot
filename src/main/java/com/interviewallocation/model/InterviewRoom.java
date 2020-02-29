package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "interview_rooms")
public class InterviewRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String name;

    public InterviewRoom() {
    }

    @Override
    public String toString() {
        return "InterviewRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
