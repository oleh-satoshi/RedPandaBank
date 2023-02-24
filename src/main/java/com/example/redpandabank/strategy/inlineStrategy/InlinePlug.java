package com.example.redpandabank.strategy.inlineStrategy;

import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class InlinePlug implements InlineHandler<Update> {
    final TranslateService translateService;
    final String NO_COMMAND = "no-command";

    public InlinePlug(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String firstName = UpdateInfo.getFirstName(update);
        Long chatId = UpdateInfo.getUserId(update);
        String content = firstName + translateService.getBySlug(NO_COMMAND);
        return new MessageSenderImpl().sendMessage(chatId, content);
    }
}
