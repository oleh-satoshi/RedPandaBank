package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.schedule.MenuButton;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ShowScheduleMenuCommandHandler implements CommandHandler<Update> {
    private final MenuButton menuButton;

    public ShowScheduleMenuCommandHandler(MenuButton menuButton) {
        this.menuButton = menuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();

        String response = "Что тебе интересно?";
        SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .replyMarkup(menuButton.getScheduleMenuButton())
                .build();
        return sendMessage;
    }
}
