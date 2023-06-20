package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleFindLessonByDay implements InlineHandler<Update> {
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;
    final MessageSender messageSender;

    public InlineScheduleFindLessonByDay(LessonService lessonService,
                                         ReplyMainMenuButton mainMenuButton,
                                         MessageSender messageSender) {
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String day = UpdateInfo.getData(update);
        Long childId = UpdateInfo.getUserId(update);
        lessonService.getLessonsByDayAndUserId(childId, day);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        Optional<String> content = Optional.of("");
        return messageSender.sendMessageWithReply(childId, content.get(), keyboard);
    }
}
