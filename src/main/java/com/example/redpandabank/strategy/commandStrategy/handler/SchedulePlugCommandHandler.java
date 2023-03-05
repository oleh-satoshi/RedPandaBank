package com.example.redpandabank.strategy.commandStrategy.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.util.UpdateInfo;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchedulePlugCommandHandler implements CommandHandler<Update> {
    final TranslateService translateService;
    final String NO_COMMAND = "no-command";

    public SchedulePlugCommandHandler(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public SendMessage handle(Update update) {
        String firstName = UpdateInfo.getFirstName(update);
        Long chatId = UpdateInfo.getUserId(update);
        String response = firstName + translateService.getBySlug(NO_COMMAND);
        return new MessageSenderImpl().sendMessage(chatId, response);
    }
}
