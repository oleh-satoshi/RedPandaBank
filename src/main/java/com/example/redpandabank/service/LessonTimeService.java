package com.example.redpandabank.service;

import com.example.redpandabank.model.LessonTime;

public interface LessonTimeService {
    LessonTime create(LessonTime lessonTime);

    LessonTime getById(Long id);

    void deleteById(Long id);
}
