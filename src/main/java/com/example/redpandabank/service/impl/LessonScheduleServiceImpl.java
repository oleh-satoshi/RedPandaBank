package com.example.redpandabank.service.impl;

import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.repository.LessonScheduleRepository;
import com.example.redpandabank.service.LessonScheduleService;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class LessonScheduleServiceImpl implements LessonScheduleService {
    final LessonScheduleRepository lessonScheduleRepository;

    public LessonScheduleServiceImpl(LessonScheduleRepository lessonScheduleRepository) {
        this.lessonScheduleRepository = lessonScheduleRepository;
    }

    @Override
    public LessonSchedule create(LessonSchedule lessonSchedule) {
        return lessonScheduleRepository.save(lessonSchedule);
    }

    @Override
    public List<LessonSchedule> findAllByLessonId(Long id) {
        return lessonScheduleRepository.findAllByLessonId(id);
    }

    @Override
    public void delete(LessonSchedule lessonSchedule) {
        lessonScheduleRepository.delete(lessonSchedule);
    }
}
