package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineShowAllDays;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class ChooseEventByDay implements InlineHandler<Update> {
    private final LessonService lessonService;
    private final InlineShowAllDays inlineShowAllDays;

    public ChooseEventByDay(LessonService lessonService, InlineShowAllDays inlineShowAllDays) {
        this.lessonService = lessonService;
        this.inlineShowAllDays = inlineShowAllDays;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        String content = "На какой день недели тебе интересно посмотреть уроки?";
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        InlineKeyboardMarkup inline = inlineShowAllDays.getInline();
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, content);
    }
}
