package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "interviewers")
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter @Setter
    private String breakStart;

    @Getter @Setter
    private String breakEnd;

    @Getter @Setter
    private long noOfHoursAvailable;

    public Interviewer() {
    }

    @Override
    public String toString() {
        return "Interviewer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breakStart='" + breakStart + '\'' +
                ", breakEnd='" + breakEnd + '\'' +
                '}';
    }
}
