package com.example.redpandabank.service.impl;

import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.repository.LessonRepository;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.vdurmont.emoji.EmojiParser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class LessonServiceImpl implements LessonService {
    final LessonRepository lessonRepository;
    final TranslateService translateService;
    final String NO_LESSONS_FOR_THE_DAY = "no-lessons-for-the-day";
    final String TEACHER = "teacher";
    final String STARTS_AT = "starts-at";
    final String LESSON_END_IN = "lesson-end-in";
    final String LESSON_DURATION = "lesson-duration";
    public static final String NEXT_LINE = "%0A";

    public LessonServiceImpl(LessonRepository lessonRepository,
                             TranslateService translateService) {
        this.lessonRepository = lessonRepository;
        this.translateService = translateService;
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

    @Override
    public List<Lesson> findLessonByChildIdAndWeekDay(Long userId, String day) {
        return lessonRepository.findLessonByChildIdAndDay(userId, day);
    }

    @Override
    public HashSet<Lesson> getSetWithAllLessonByChildId(Long childId) {
        return lessonRepository.findAllByChildId(childId);
    }

    @Override
    public List<Lesson> getAllByChildId(Long childId) {
        return lessonRepository.getAllByChildId(childId);
    }

    @Override
    public Optional<String> getLessonsByDayAndChildId(Long childId, String day) {
        List<Lesson> lessonByDay = findLessonByChildIdAndWeekDay(childId, day);
        if (lessonByDay.isEmpty()) {
            new MessageSenderImpl().sendMessageViaURL(childId, translateService.getBySlug(NO_LESSONS_FOR_THE_DAY));
            return Optional.empty();
        }
        new MessageSenderImpl().sendMessageViaURL(childId, EmojiParser.parseToUnicode(
                ":calendar: " + "<b>" + day + "</b>"));
        for (Lesson lesson : lessonByDay) {
            new MessageSenderImpl().sendMessageViaURL(childId, parseLessonForUrl(lesson));
        }
        return Optional.empty();
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find lesson by id: " + id));
    }

    @Override
    public Boolean checkAllByTitle(String title, Long childId) {
        return lessonRepository.findAllByTitleAndChildId(title, childId).isEmpty();
    }

    @Override
    public Lesson findLessonByTitle(Long childId, String title) {
        return lessonRepository.findLessonByChildIdAndTitle(childId, title);
    }

    @Override
    public String getDuration(Integer duration) {
        if (duration != null) {
            return duration > 60 ? " hours" : " minutes";
        }
        return "0";
    }

    @Override

    public String getStartTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>" + lessonSchedule.getLessonStartTime() + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1) + NEXT_LINE;
    }

    @Override
    public String getFinishTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>"
                        + lessonSchedule.getLessonStartTime().plusMinutes(lesson.getDuration()) + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1) + NEXT_LINE;
    }

    @Override
    public String getInfoLessonByIdAndSendByUrl(Long id) {
        return parseLessonForUrl(getById(id));
    }

    @Transactional
    @Override
    public void deleteLessonByTitleAndChildId(String title, Long id) {
        lessonRepository.deleteLessonByTitleAndChildId(title, id);
    }

    @Override
    public List<Lesson> findAllByChildIdWithoutLessonSchedule(Long childId) {
        return lessonRepository.findAllByChildIdWithoutLessonSchedule(childId);
    }

    @Override
    public Lesson findLessonByLessonSchedules(LessonSchedule lessonSchedule) {
        return lessonRepository.findLessonByLessonSchedules(lessonSchedule);
    }

    private String parseLessonForUrl(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":school_satchel: " + "<b>" + lesson.getTitle() + "</b>" + NEXT_LINE)
                .append(translateService.getBySlug(TEACHER) + "<i>" + lesson.getTeacher() + "</i>" + NEXT_LINE)
                .append(":bell: " + translateService.getBySlug(STARTS_AT) + getStartTime(lesson))
                .append(":checkered_flag: " + translateService.getBySlug(LESSON_END_IN) + getFinishTime(lesson))
                .append(":clock8: " + translateService.getBySlug(LESSON_DURATION) + "<b>" + lesson.getDuration() + "</b>" + getDuration(lesson.getDuration()) + NEXT_LINE);
        return EmojiParser.parseToUnicode(stringBuilder.toString());
    }
}
