package com.example.redpandabank.service;

import com.example.redpandabank.repository.LessonRepository;
import com.example.redpandabank.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
    public Long getLessonsQuantity(Long userId) {
        return lessonRepository.getLessonsQuantity(userId);
    }

//    @Override
//    public List<Lesson> getLessonsByDay(Long userId, String day) {
//        return lessonRepository.findLessonByChildIdAndWeekDay(userId, day);
//    }

    @Override
    public List<Lesson> findLessonByChildIdAndWeekDay(Long userId, String day) {
        return lessonRepository.findLessonByChildIdAndWeekDay(userId, day);
    }

    @Override
    public List<Lesson> findAllByChildId(Long childId) {
        return lessonRepository.findAllByChildId(childId);
    }

    @Override
    public String getStringLessonByDay(Long childId, String day) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Lesson> lessonByDay = findLessonByChildIdAndWeekDay(childId, day);
        for (Lesson lesson : lessonByDay) {
            stringBuilder.append(lesson.getTitle() + "\n")
                            .append(lesson.getDescription() + "\n")
                            .append("Идет " + lesson.getDuration() + getDuration(lesson.getDuration()) + "\n")
                            .append("Урок начинается в " + lesson.getLessonsStartTime() + "\n")
                            .append("ID урока " + lesson.getId())
                    .append("\n\n");
        }
            return day + "\n\n" + stringBuilder;
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find lesson by id: " + id));
    }

//    @Override
//    public List<Lesson> getByUserId(Long userId) {
//        return lessonRepository.findAllByChildId(userId);
//    }

    private String getDuration(Integer duration) {
        return duration > 60 ? " часа" : " минут";
    }
}
