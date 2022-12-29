package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;

import java.util.List;

public interface LessonService {
    Lesson create(Lesson category);

    void deleteById(Long id);

    Long getLessonsQuantity(Long id);


    List<Lesson> findLessonByChildIdAndWeekDay(Long userId, String day);

    List<Lesson> findAllByChildId(Long childId);

    String getStringLessonByDay(Long userId, String day);

    Lesson getById(Long id);


//    List<Lesson> getByUserId(Long userId);

//    List<Lesson> getLessonsByDay(Long userId, String day);

}
