package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleShowAllDaysButton;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class InlineScheduleChooseEventByDay implements InlineHandler<Update> {
    private final InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton;

    public InlineScheduleChooseEventByDay(InlineScheduleShowAllDaysButton inlineScheduleShowAllDaysButton) {
        this.inlineScheduleShowAllDaysButton = inlineScheduleShowAllDaysButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        String content = "На какой день недели тебе интересно посмотреть уроки?";
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        InlineKeyboardMarkup keyboard = inlineScheduleShowAllDaysButton.getKeyboard();
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, content);
    }
}
