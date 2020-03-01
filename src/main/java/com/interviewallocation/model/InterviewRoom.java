package com.interviewallocation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "interview_rooms")
public class InterviewRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interviewRoom_id")
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(unique = true,nullable = false)
    private String name;

    public InterviewRoom() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewRoom that = (InterviewRoom) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "InterviewRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
