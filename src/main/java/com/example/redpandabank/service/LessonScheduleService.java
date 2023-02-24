package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;

import java.util.List;

public interface LessonScheduleService {
    LessonSchedule create(LessonSchedule lessonSchedule);


    List<LessonSchedule> findAllByLessonId(Long id);

    void delete(LessonSchedule lessonSchedule);
}
