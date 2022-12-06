package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;

public interface LessonService {
    Lesson create(Lesson category);

    Lesson getById(Long id);

    void deleteById(Long id);

//    List<Lesson> getBeetween(LocalDateTime from, LocalDateTime to);
}
