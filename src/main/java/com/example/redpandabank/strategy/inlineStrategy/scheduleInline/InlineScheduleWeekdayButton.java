package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleWeekdayButton implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final TranslateService translateService;
    final String SAVE_DAY = "save-day";

    public InlineScheduleWeekdayButton(LessonService lessonService,
                                       LessonScheduleService lessonScheduleService,
                                       TranslateService translateService) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String content;
        String day;
        String inputDay = UpdateInfo.getData(update);
        Long userId = UpdateInfo.getUserId(update);

        if (inputDay.equals(Command.SAVE_EVENT_MONDAY.getName())) {
            day = WeekDay.MONDAY.getDay();
            createLessonSchedule(day, userId);
        } else if (inputDay.equals(Command.SAVE_EVENT_TUESDAY.getName())) {
            day = WeekDay.TUESDAY.getDay();
            createLessonSchedule(day, userId);
        } else if (inputDay.equals(Command.SAVE_EVENT_WEDNESDAY.getName())) {
            day = WeekDay.WEDNESDAY.getDay();
            createLessonSchedule(day, userId);
        } else if (inputDay.equals(Command.SAVE_EVENT_THURSDAY.getName())) {
            day = WeekDay.THURSDAY.getDay();
            createLessonSchedule(day, userId);
        } else if (inputDay.equals(Command.SAVE_EVENT_FRIDAY.getName())) {
            day = WeekDay.FRIDAY.getDay();
            createLessonSchedule(day, userId);
        } else if (inputDay.equals(Command.SAVE_EVENT_SATURDAY.getName())) {
            day = WeekDay.SATURDAY.getDay();
            createLessonSchedule(day, userId);
        } else {
            day = WeekDay.SUNDAY.getDay();
            createLessonSchedule(day, userId);
        }

        content = translateService.getBySlug(SAVE_DAY);
        return new MessageSenderImpl().sendMessage(userId, content);
    }

    private void createLessonSchedule(String day, Long userId) {
        //TODO check the logic when will repair TelegramBot
        List<LessonSchedule> allByChildId = lessonService.getAllByChildId(userId).stream()
                .map(lesson -> lesson.getLessonSchedules())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        LessonSchedule lessonSchedule = allByChildId.size() == 1 ? allByChildId.get(0) : allByChildId.get(allByChildId.size() - 1);
        lessonSchedule.setDay(day);
        lessonScheduleService.create(lessonSchedule);
        HashSet<Lesson> lessonsSet = lessonService.getSetWithAllLessonByChildId(userId);
        List<Lesson> lessons = new ArrayList<>(lessonsSet);
        Lesson lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
        List<LessonSchedule> lessonSchedules = new ArrayList<>();
        lessonSchedules.add(lessonSchedule);
        lesson.setLessonSchedules(lessonSchedules);
        lessonService.create(lesson);
    }
}
