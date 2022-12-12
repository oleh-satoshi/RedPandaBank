package com.example.redpandabank.repository;

import com.example.redpandabank.model.LessonTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonTimeRepository extends JpaRepository<LessonTime, Long> {
}
