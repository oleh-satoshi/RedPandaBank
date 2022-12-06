package com.example.redpandabank.service;

import com.example.redpandabank.repository.LessonRepository;
import com.example.redpandabank.model.Lesson;
import org.springframework.stereotype.Service;


@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Lesson create(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find lesson by id: " + id));
    }
}
