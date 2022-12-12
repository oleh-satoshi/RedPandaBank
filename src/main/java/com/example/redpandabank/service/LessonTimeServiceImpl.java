package com.example.redpandabank.service;

import com.example.redpandabank.model.LessonTime;
import com.example.redpandabank.repository.LessonTimeRepository;
import org.springframework.stereotype.Service;

@Service
public class LessonTimeServiceImpl implements LessonTimeService {
    private LessonTimeRepository lessonTimeRepository;

    public LessonTimeServiceImpl(LessonTimeRepository lessonTimeRepository) {
        this.lessonTimeRepository = lessonTimeRepository;
    }
    @Override
    public LessonTime create(LessonTime lessonTime) {
        return lessonTimeRepository.save(lessonTime);
    }

    @Override
    public LessonTime getById(Long id) {
        return lessonTimeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find TimeRepository by id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        lessonTimeRepository.deleteById(id);
    }
}
