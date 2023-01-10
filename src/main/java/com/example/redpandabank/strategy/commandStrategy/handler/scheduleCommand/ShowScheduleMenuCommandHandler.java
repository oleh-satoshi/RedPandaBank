package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.schedule.MenuButton;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ShowScheduleMenuCommandHandler implements CommandHandler<Update> {
    final MenuButton menuButton;

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
