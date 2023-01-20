package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InlineScheduleFindLessonByDay implements InlineHandler<Update> {
    private final LessonService lessonService;

    public InlineScheduleFindLessonByDay(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String day = update.getCallbackQuery().getData();
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        lessonService.getLessonsByDayAndChildId(childId, day);
        return null;
    }
}
