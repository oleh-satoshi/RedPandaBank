package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.schedule.InlineScheduleMenuButton;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ScheduleMenuShowCommandHandler implements CommandHandler<Update> {
    final InlineScheduleMenuButton inlineScheduleMenuButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String WHAT_INTERESTED_IN = "what-interested-in";

    public ScheduleMenuShowCommandHandler(InlineScheduleMenuButton inlineScheduleMenuButton,
                                          TranslateService translateService,
                                          MessageSender messageSender) {
        this.inlineScheduleMenuButton = inlineScheduleMenuButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();
        ReplyKeyboard keyboard = inlineScheduleMenuButton.getKeyboard();
        String response = translateService.getBySlug(WHAT_INTERESTED_IN);
        return messageSender.sendMessageWithInline(userId, response, keyboard);
    }
}
