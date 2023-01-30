package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleEditMenuButton;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleEdit implements InlineHandler<Update> {
    final InlineScheduleEditMenuButton inlineScheduleEditMenuButton;

    public InlineScheduleEdit(InlineScheduleEditMenuButton inlineScheduleEditMenuButton) {
        this.inlineScheduleEditMenuButton = inlineScheduleEditMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {

        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String content = "Это меню редактирования";
        InlineKeyboardMarkup keyboard = inlineScheduleEditMenuButton.getKeyboard();
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, content);
    }
}
