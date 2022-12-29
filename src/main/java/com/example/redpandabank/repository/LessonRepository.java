package com.example.redpandabank.repository;

import com.example.redpandabank.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
//    @Query("from Lesson l where l.childId = ?1 and l.title = ?2")
//    Lesson findByUserIdAndTitle(Long userId, String title);

    @Query(value = "select COUNT(*) from lessons where child_id = ?1", nativeQuery = true)
    Long getLessonsQuantity(Long userId);

//    @Query(value = "from Lesson l where l.childId = ?1")
//    List<Lesson> getByUserId(Long userId);

    List<Lesson> findLessonByChildIdAndWeekDay(Long childId, String day);

    List<Lesson> findAllByChildId(Long childId);
}
