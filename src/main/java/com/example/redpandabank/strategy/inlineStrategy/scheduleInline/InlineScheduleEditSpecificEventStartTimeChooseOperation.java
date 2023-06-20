package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleEditSpecificEventStartTimeButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditSpecificEventStartTimeChooseOperation implements InlineHandler<Update> {
    final InlineScheduleEditSpecificEventStartTimeButton chooseOperationButton;
    final LessonService lessonService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String OPTION_FOR_START_TIME = "option-for-start-time";

    public InlineScheduleEditSpecificEventStartTimeChooseOperation(InlineScheduleEditSpecificEventStartTimeButton
                                                                           chooseOperationButton,
                                                                   LessonService lessonService,
                                                                   TranslateService translateService,
                                                                   MessageSender messageSender) {
        this.chooseOperationButton = chooseOperationButton;
        this.lessonService = lessonService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Long lessonId = parseId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        String response = getResponse(lesson);
        InlineKeyboardMarkup inline = chooseOperationButton.getKeyboard(lesson);
        return messageSender.sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(OPTION_FOR_START_TIME)
                + " <i>\"" + lesson.getTitle() + "\"</i> !";
    }

    private Long parseId(String text) {
        if (text.split(Separator.QUOTE_SEPARATOR).length == 3) {
            return Long.parseLong(text.split(Separator.QUOTE_SEPARATOR)[1]);
        }
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
