package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class InlinePlug implements InlineHandler<Update> {
    final TranslateService translateService;
    final MessageSender messageSender;
    final String NO_COMMAND = "no-command";

    public InlinePlug(TranslateService translateService,
                      MessageSender messageSender) {
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String firstName = UpdateInfo.getFirstName(update);
        Long chatId = UpdateInfo.getUserId(update);
        String content = firstName + translateService.getBySlug(NO_COMMAND);
        return messageSender.sendMessage(chatId, content);
    }
}
