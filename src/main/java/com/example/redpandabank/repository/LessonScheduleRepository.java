package com.example.redpandabank.repository;

import com.example.redpandabank.model.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Long> {
    @Query(value = "select * from lessons_schedule ls where ls.lesson_id = ?1", nativeQuery = true)
    List<LessonSchedule> findAllByLessonId(Long id);
}
