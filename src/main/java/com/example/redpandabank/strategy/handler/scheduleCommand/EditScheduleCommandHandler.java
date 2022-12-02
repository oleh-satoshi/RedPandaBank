package com.example.redpandabank.strategy.handler.scheduleCommand;

import com.example.redpandabank.buttons.schedule.EditMenuButton;
import com.example.redpandabank.strategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EditScheduleCommandHandler implements CommandHandler {
    private final EditMenuButton editMenuButton;

    public EditScheduleCommandHandler(EditMenuButton editMenuButton) {
        this.editMenuButton = editMenuButton;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.getMessage().getChatId();

        String response = "Это меню редактирования";
        SendMessage sendMessage =  SendMessage.builder()
                .text(response)
                .chatId(userId)
                .replyMarkup(editMenuButton.getScheduleEditMenuButton())
                .build();
        return sendMessage;
    }
}
