package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleEditEventFieldButton;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditSpecificExistingEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String WHAT_FIELD_IN_THE_LESSON = "what-field-in-the-lesson";
    final String WANT_TO_FIX = "want-to-fix";

    public InlineScheduleEditSpecificExistingEvent(LessonService lessonService,
                                                   InlineScheduleEditEventFieldButton
                                                           inlineScheduleEditEventFieldButton,
                                                   TranslateService translateService,
                                                   MessageSender messageSender) {
        this.lessonService = lessonService;
        this.inlineScheduleEditEventFieldButton = inlineScheduleEditEventFieldButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Long lessonId = parseId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        Integer messageId = UpdateInfo.getMessageId(update);
        InlineKeyboardMarkup inline = inlineScheduleEditEventFieldButton.getKeyboard(lesson);
        String response = getResponse(lesson);
        return messageSender.sendEditMessageWithInline(userId, messageId, inline, response);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(WHAT_FIELD_IN_THE_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i> "
                + translateService.getBySlug(WANT_TO_FIX);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
