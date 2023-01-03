package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.buttons.main.BackToMainMenuButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
public class InlineScheduleFindLessonByDay implements InlineHandler<Update> {
    private final BackToMainMenuButton backToMainMenuButton;
    private final LessonService lessonService;

    public InlineScheduleFindLessonByDay(BackToMainMenuButton backToMainMenuButton, LessonService lessonService) {
        this.backToMainMenuButton = backToMainMenuButton;
        this.lessonService = lessonService;
    }


    @Override
    public BotApiMethod<?> handle(Update update) {
        String day = update.getCallbackQuery().getData();
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        String stringLessonTimesByDay = lessonService.getLessonsByDayAndChildId(childId, day);
        return null;
    }
}
