package com.example.redpandabank.repository;

import com.example.redpandabank.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "select COUNT(*) from lessons where child_id = ?1", nativeQuery = true)
    Long getLessonsQuantity(Long userId);

@Query(value = "select l.lesson_id, l.child_id, title, teacher, duration from lessons as l inner join lessons_schedule as ls on ls.lesson_id = l.lesson_id" +
        " where ls.child_id = ?1 and day = ?2 order by ls.lesson_start_time", nativeQuery = true)
    List<Lesson> findLessonByChildIdAndDay(Long childId, String day);

    List<Lesson> findAllByChildId(Long childId);

    HashSet<Lesson> findAllByTitleAndChildId(String title, Long childId);

    Lesson findLessonByTitleAndChildId(Long childId, String title);
}
