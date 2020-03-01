package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "interviewers")
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interviewer_id")
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(unique = true,nullable = false)
    private String name;

    @Getter @Setter
    private LocalTime breakStart;

    @Getter @Setter
    private LocalTime breakEnd;

    @Getter @Setter
    private long noOfHoursAvailable;

    public Interviewer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interviewer that = (Interviewer) o;
        return noOfHoursAvailable == that.noOfHoursAvailable &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(breakStart, that.breakStart) &&
                Objects.equals(breakEnd, that.breakEnd);
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
