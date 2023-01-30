package com.example.redpandabank.strategy.inlineStrategy.mainMenu;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@PackagePrivate
@Component
public class InlineToMainMenu implements InlineHandler<Update> {
    final ReplyMainMenuButton mainMenuButton;

    public InlineToMainMenu(ReplyMainMenuButton mainMenuButton) {
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String response = "Мы снова в главном меню!";
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        return new MessageSenderImpl().sendMessageWithReply(chatId, response, keyboard);
    }
}
