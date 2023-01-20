package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineEditScheduleMenuButton;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class EditSchedule implements InlineHandler<Update> {
    final InlineEditScheduleMenuButton inlineEditScheduleMenuButton;

    public EditSchedule(InlineEditScheduleMenuButton inlineEditScheduleMenuButton) {
        this.inlineEditScheduleMenuButton = inlineEditScheduleMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {

        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String content = "Это меню редактирования";
        InlineKeyboardMarkup inline = inlineEditScheduleMenuButton.getInline();
        EditMessageText editMessageText = new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, content);
        return editMessageText;
    }
}
