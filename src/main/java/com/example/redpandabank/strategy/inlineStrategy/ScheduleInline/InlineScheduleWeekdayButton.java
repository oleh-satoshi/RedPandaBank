package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.WeekDay;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class InlineScheduleWeekdayButton implements InlineHandler<Update> {
    private final BackToMainMenuButton backToMainMenuButton;
    private final LessonService lessonService;


    public InlineScheduleWeekdayButton(BackToMainMenuButton backToMainMenuButton,
                                       LessonService lessonService) {
        this.backToMainMenuButton = backToMainMenuButton;
        this.lessonService = lessonService;
    }


    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        String day;
        String inputDay = update.getCallbackQuery().getData();
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        List<Lesson> lessons = lessonService.findAllByChildId(userId);
        Lesson  lesson = (lessons.size() == 1) ? lessons.get(0) : lessons.get(lessons.size() - 1);
        if (inputDay.equals(Command.SAVE_EVENT_MONDAY.getName())) {
            day = WeekDay.MONDAY.getDay();
            lesson.setWeekDay(day);
        } else if (inputDay.equals(Command.SAVE_EVENT_TUESDAY.getName())) {
            day = WeekDay.TUESDAY.getDay();
            lesson.setWeekDay(day);
        } else if (inputDay.equals(Command.SAVE_EVENT_WEDNESDAY.getName())) {
            day = WeekDay.WEDNESDAY.getDay();
            lesson.setWeekDay(day);
        } else if (inputDay.equals(Command.SAVE_EVENT_THURSDAY.getName())) {
            day = WeekDay.THURSDAY.getDay();
            lesson.setWeekDay(day);
        } else if (inputDay.equals(Command.SAVE_EVENT_FRIDAY.getName())) {
            day = WeekDay.FRIDAY.getDay();
            lesson.setWeekDay(day);
        } else if (inputDay.equals(Command.SAVE_EVENT_SATURDAY.getName())) {
            day = WeekDay.SATURDAY.getDay();
            lesson.setWeekDay(day);
        } else if (inputDay.equals(Command.SAVE_EVENT_SUNDAY.getName())) {
            day = WeekDay.SUNDAY.getDay();
            lesson.setWeekDay(day);
        }
        lessonService.create(lesson);
        response = "Отлично! Мы урок сохранили!";

        return new SendMessage().builder()
                        .chatId(update.getCallbackQuery().getMessage().getChatId())
                        .text(response)
                        .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                        .build();
    }
}
