package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchedulePlugCommandHandler implements CommandHandler<Update> {
    final MessageSender messageSender;
    final TranslateService translateService;
    final String NO_COMMAND = "no-command";

    public SchedulePlugCommandHandler(MessageSender messageSender,
                                      TranslateService translateService) {
        this.messageSender = messageSender;
        this.translateService = translateService;
    }

    @Override
    public SendMessage handle(Update update) {
        String firstName = UpdateInfo.getFirstName(update);
        Long chatId = UpdateInfo.getUserId(update);
        String response = firstName + translateService.getBySlug(NO_COMMAND);
        return messageSender.sendMessage(chatId, response);
    }
}
