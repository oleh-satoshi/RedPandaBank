package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService{
    private LessonRepository lessonRepository;

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

//    @Override
//    public List<Lesson> getBeetween(LocalDateTime from, LocalDateTime to) {
//        return lessonRepository.;
//    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find lesson by id: " + id));
    }



}
