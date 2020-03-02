package com.interviewallocation.repository;

import com.interviewallocation.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview,Long> {
}
