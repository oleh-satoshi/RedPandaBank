package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.model.WeekDay;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class InlineScheduleWeekdayButton implements InlineHandler<Update> {
    private final BackToMainMenuButton backToMainMenuButton;
    private final LessonService lessonService;
    private final LessonScheduleService lessonScheduleService;


    public InlineScheduleWeekdayButton(BackToMainMenuButton backToMainMenuButton,
                                       LessonService lessonService,
                                       LessonScheduleService lessonScheduleService) {
        this.backToMainMenuButton = backToMainMenuButton;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
    }


    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        String day;
        String inputDay = update.getCallbackQuery().getData();
        Long userId = update.getCallbackQuery().getMessage().getChatId();

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
        } else if (inputDay.equals(Command.SAVE_EVENT_SUNDAY.getName())) {
            day = WeekDay.SUNDAY.getDay();
            createLessonSchedule(day, userId);
        }

        response= "День недели сохранили!";


        return new SendMessage().builder()
                        .chatId(update.getCallbackQuery().getMessage().getChatId())
                        .text(response)
                        .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                        .build();
    }

    private void createLessonSchedule(String day, Long userId) {
        List<LessonSchedule> allByChildId = lessonScheduleService.findAllByChildId(userId);
        LessonSchedule lessonSchedule = allByChildId.size() == 1 ? allByChildId.get(0) : allByChildId.get(allByChildId.size() - 1);
        lessonSchedule.setDay(day);
        lessonScheduleService.create(lessonSchedule);
        List<Lesson> lessons = lessonService.findAllByChildId(userId);
        Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
        List<LessonSchedule> lessonSchedules = new ArrayList<>();
        lessonSchedules.add(lessonSchedule);
        lesson.setLessonSchedules(lessonSchedules);
        lessonService.create(lesson);
    }
}
