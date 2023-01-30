package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleMenuButton;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@PackagePrivate
@Component
public class InlineShowMainMenu implements InlineHandler<Update> {
    final InlineScheduleMenuButton inlineScheduleMenuButton;

    public InlineShowMainMenu(InlineScheduleMenuButton inlineScheduleMenuButton) {
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String response = "Что тебе интересно?";
        InlineKeyboardMarkup keyboard = inlineScheduleMenuButton.getKeyboard();
        return new MessageSenderImpl().sendEditMessageWithInline(userId, messageId, keyboard, response);
    }
}
