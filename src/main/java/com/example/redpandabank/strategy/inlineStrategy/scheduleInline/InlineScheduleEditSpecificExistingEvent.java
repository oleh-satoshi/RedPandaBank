package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleEditEventFieldButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
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
    final String WHAT_FIELD_IN_THE_LESSON = "what-field-in-the-lesson";
    final String WANT_TO_FIX = "want-to-fix";

    public InlineScheduleEditSpecificExistingEvent(LessonService lessonService,
                                                   InlineScheduleEditEventFieldButton
                                                           inlineScheduleEditEventFieldButton,
                                                   TranslateService translateService) {
        this.lessonService = lessonService;
        this.inlineScheduleEditEventFieldButton = inlineScheduleEditEventFieldButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Long lessonId = parseId(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.getById(lessonId);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        InlineKeyboardMarkup inline = inlineScheduleEditEventFieldButton.getKeyboard(lesson);
        String content = translateService.getBySlug(WHAT_FIELD_IN_THE_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i> "
                + translateService.getBySlug(WANT_TO_FIX);
        return new MessageSenderImpl()
                .sendEditMessageWithInline(childId, messageId, inline, content);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
