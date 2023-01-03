package com.example.redpandabank.service;

import com.example.redpandabank.repository.LessonRepository;
import com.example.redpandabank.model.Lesson;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final static String NEXT_LINE = "%0A";

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
        return lessonRepository.findLessonByChildIdAndDay(userId, day);
    }

    @Override
    public List<Lesson> findAllByChildId(Long childId) {
        return lessonRepository.findAllByChildId(childId);
    }

    @Override
    public String getLessonsByDayAndChildId(Long childId, String day) {
        List<Lesson> lessonByDay = findLessonByChildIdAndWeekDay(childId, day);
        MessageSender messageSender = new MessageSenderImpl();
        messageSender.sendMessageToTelegram(childId,  EmojiParser.parseToUnicode(":calendar: " + "<b>" + day + "</b>"));
        for (Lesson lesson : lessonByDay) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(":school_satchel: " + "<b>" + lesson.getTitle() + "</b>" + NEXT_LINE)
                            .append(":mortar_board: " + "<i>" + lesson.getTeacher() + "</i>" + NEXT_LINE)
                            .append(":bell: " + "Начинается в " + getStartTime(lesson))
                            .append(":clock8: " + "Идет " + "<b>" + lesson.getDuration() + "</b>" + getDuration(lesson.getDuration()) + NEXT_LINE)
                            .append(":checkered_flag: " + "Конец в " + getFinishTime(lesson));

            messageSender.sendMessageToTelegram(childId, EmojiParser.parseToUnicode(stringBuilder.toString() ));
        }

        return day;
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find lesson by id: " + id));
    }

    @Override
    public Boolean findAllByTitle(String title, Long childId) {
        return lessonRepository.findAllByTitleAndChildId(title, childId).contains(title);
    }

//    @Override
//    public List<Lesson> getByUserId(Long userId) {
//        return lessonRepository.findAllByChildId(userId);
//    }

    private String getDuration(Integer duration) {
        return duration > 60 ? " часа" : " минут";
    }

    private String getStartTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>" + lessonSchedule.getLessonStartTime() + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1) + NEXT_LINE;
    }

    private String getFinishTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> "<b>" + lessonSchedule.getLessonStartTime().plusMinutes(lesson.getDuration()) + "</b>")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1) + NEXT_LINE;
    }


}
