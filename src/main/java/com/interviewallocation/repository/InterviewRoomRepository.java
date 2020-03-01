package com.interviewallocation.repository;

import com.interviewallocation.model.InterviewRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRoomRepository extends JpaRepository<InterviewRoom, Long> {
}
