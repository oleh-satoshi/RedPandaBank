package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.schedule.InlineScheduleMenuButton;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level= AccessLevel.PRIVATE)@Component
public class ScheduleMenuShowCommandHandler implements CommandHandler<Update> {
    final InlineScheduleMenuButton inlineScheduleMenuButton;

    public ScheduleMenuShowCommandHandler(InlineScheduleMenuButton inlineScheduleMenuButton) {
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();
        ReplyKeyboard keyboard = inlineScheduleMenuButton.getKeyboard();
        String response = "Что тебе интересно?";
        return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
    }
}
