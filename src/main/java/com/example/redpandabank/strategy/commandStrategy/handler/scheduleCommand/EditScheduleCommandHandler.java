package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.keyboard.main.BackToMainMenuButton;
import com.example.redpandabank.keyboard.schedule.EditMenuButton;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EditScheduleCommandHandler implements CommandHandler<Update> {
    private final EditMenuButton editMenuButton;
    private final BackToMainMenuButton backToMainMenuButton;

    public EditScheduleCommandHandler(EditMenuButton editMenuButton,
                                      BackToMainMenuButton backToMainMenuButton) {
        this.editMenuButton = editMenuButton;
        this.backToMainMenuButton = backToMainMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();
        String response = "Это меню редактирования";
        SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .replyMarkup(backToMainMenuButton.getBackToMainMenuButton())
                .replyMarkup(editMenuButton.getScheduleEditMenuButton())
                .build();
        return sendMessage;
    }
}
