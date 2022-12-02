package com.example.redpandabank.strategy.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class StartCommandHandler implements CommandHandler {

    @Override
    public SendMessage handle(Update update) {
        String response;
        Long userId = update.getMessage().getChatId();

        response = "Да откроется биржа!\n\n"
                + "Теперь у тебя есть собственный цифровой кошелек, пока что у тебя на нем <b>"
                + "За тяжелые задания ты получишь больше баллов, но выполнять придется всё :)";
        SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .build();
        return sendMessage;
    }
}
