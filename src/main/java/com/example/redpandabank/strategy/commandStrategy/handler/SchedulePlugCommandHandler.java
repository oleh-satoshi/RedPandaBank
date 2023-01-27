package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.util.UpdateInfo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SchedulePlugCommandHandler implements CommandHandler<Update> {
    @Override
    public SendMessage handle(Update update) {
        String firstName = update.getMessage().getChat().getFirstName();
        Long chatId = UpdateInfo.getUserId(update);
        String response = firstName + ", такой команды нет!";
        return new MessageSenderImpl().sendMessage(chatId, response);
    }
}
