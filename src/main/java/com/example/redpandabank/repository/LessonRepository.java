package com.example.redpandabank.repository;

import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "select COUNT(*) from lessons "
            + "where user_id = ?1 and is_deleted = false", nativeQuery = true)
    Long getLessonsQuantity(Long userId);

    @Query(value = "select l.id, l.user_id, l.is_deleted, title, teacher, duration "
            + "from lessons as l inner join lessons_schedule as ls on ls.lesson_id = l.id"
            + " where ls.user_id = ?1 and day = ?2 and l.is_deleted = false"
            + " order by ls.lesson_start_time", nativeQuery = true)
    List<Lesson> findLessonByUserIdAndDay(Long userId, String day);

    @Query(value = "select l.id, l.user_id, l.is_deleted, title, teacher, duration "
            + "from lessons as l inner join lessons_schedule as ls on ls.lesson_id = l.id"
            + " where ls.user_id = ?1 and l.is_deleted = false "
            + "order by l.id, ls.lesson_start_time", nativeQuery = true)
    HashSet<Lesson> findAllByChildId(Long childId);

    @Query(value = "select l.id, l.user_id, l.is_deleted, title, teacher, duration "
            + "from lessons as l where l.is_deleted = false "
            + "order by l.id", nativeQuery = true)
    List<Lesson> findAllByUserId(Long childId);

    HashSet<Lesson> findAllByTitleAndUserId(String title, Long childId);

    Lesson findLessonByUserIdAndTitle(Long childId, String title);

    void deleteLessonByTitleAndUserId(String title, Long id);

    Lesson findLessonByLessonSchedules(LessonSchedule lessonSchedule);

    List<Lesson> getAllByUserId(Long childId);
}
