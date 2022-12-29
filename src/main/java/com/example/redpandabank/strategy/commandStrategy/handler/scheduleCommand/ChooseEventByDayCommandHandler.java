package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.buttons.schedule.InlineShowAllDays;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChooseEventByDayCommandHandler implements CommandHandler<Update> {
    private final LessonService lessonService;
    private final InlineShowAllDays inlineShowAllDays;

    public ChooseEventByDayCommandHandler(LessonService lessonService, InlineShowAllDays inlineShowAllDays) {
        this.lessonService = lessonService;
        this.inlineShowAllDays = inlineShowAllDays;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        return new SendMessage().builder()
                .chatId(update.getMessage().getChatId())
                .text("На какой день недели тебе интересно посмотреть уроки?")
                .replyMarkup(inlineShowAllDays.getInline())
                .build();
    }
}
