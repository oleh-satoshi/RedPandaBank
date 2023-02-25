package com.example.redpandabank.strategy.commandStrategy.handler;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.UpdateInfo;
import com.vdurmont.emoji.EmojiParser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ScheduleStartCommandHandler implements CommandHandler<Update> {
    final ReplyMainMenuButton replyMainMenuButton;
    final ChildService childService;
    final TranslateService translateService;
    final String HELLO = "hello";
    final String WE_ARE_MET = "we-are-met";
    boolean isInitialize;

    public ScheduleStartCommandHandler(ReplyMainMenuButton replyMainMenuButton,
                                       ChildService childService, TranslateService translateService) {
        this.replyMainMenuButton = replyMainMenuButton;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public SendMessage handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        isInitialize = childService.findById(userId).isPresent();
        if (!isInitialize) {
            childService.createChild(userId);
            response = EmojiParser.parseToUnicode(translateService.getBySlug(HELLO));
            ReplyKeyboardMarkup keyboard = replyMainMenuButton.getKeyboard();
            return new MessageSenderImpl().sendMessageWithReply(userId, response,keyboard);
        } else {
            response = EmojiParser.parseToUnicode(translateService.getBySlug(WE_ARE_MET));
            return new MessageSenderImpl().sendMessage(userId, response);
        }
    }
}
