package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleFindLessonByDay implements InlineHandler<Update> {
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;

    public InlineScheduleFindLessonByDay(LessonService lessonService,
                                         ReplyMainMenuButton mainMenuButton) {
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String day = UpdateInfo.getData(update);
        Long childId = UpdateInfo.getUserId(update);
        lessonService.getLessonsByDayAndChildId(childId, day);
        ReplyKeyboardMarkup keyboard = mainMenuButton.getKeyboard();
        Optional<String> content = Optional.of("");
        return new MessageSenderImpl().sendMessageWithReply(childId, content.get(), keyboard);
    }
}
