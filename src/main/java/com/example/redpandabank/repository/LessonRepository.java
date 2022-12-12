package com.example.redpandabank.repository;

import com.example.redpandabank.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("from Lesson l where l.childId = ?1 and l.title = ?2")
    Lesson findByUserId(Long userId, String title);

    @Query(value = "select COUNT(id) from lessons", nativeQuery = true)
    Long getLessonsQuantity();
}
