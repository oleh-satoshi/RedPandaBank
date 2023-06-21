package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface LessonService {

    Lesson create(Lesson category);

    void deleteById(Long id);

    Long getLessonsQuantity(Long id);

    List<Lesson> findLessonByUserIdAndWeekDay(Long userId, String day);

    HashSet<Lesson> getSetWithAllLessonByChildId(Long childId);

    List<Lesson> getAllByChildId(Long childId);

    Optional<String> getLessonsByDayAndUserId(Long userId, String day);

    Lesson getById(Long id);

    Boolean checkAllByTitle(String title, Long childId);

    Lesson findLessonByTitle(Long childId, String title);

    String getDuration(Integer duration);

    String getStartTime(Lesson lesson);

    String getFinishTime(Lesson lesson);

    String getLessonInfoByIdForSendByUrl(Long id);

    void deleteLessonByTitleAndChildId(String title, Long id);

    List<Lesson> findAllByUserId(Long childId);

    String getLessonInfoByLessonAndLessonScheduleForSendByUrl(Lesson lesson, LessonSchedule lessonSchedule);

    String parseLessonForUrlWithLessonSchedule(Lesson lesson, LessonSchedule lessonSchedule);
}
