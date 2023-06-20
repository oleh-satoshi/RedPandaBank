package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEventField implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final String EDIT_EVENT_FIELD = "edit-event-field";
    final MessageSender messageSender;

    public InlineScheduleEditEventField(LessonService lessonService,
                                        ChildService childService,
                                        TranslateService translateService,
                                        MessageSender messageSender) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Long lessonId = parseId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        setEditSpecificFieldStateAndLessonId(childId, lesson);
        String response = getResponse(lesson);
        return messageSender.sendEditMessage(childId, messageId, response);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(EDIT_EVENT_FIELD)
                + " <i>\""
                + lesson.getTitle() + "\"</i> !";
    }

    private void setEditSpecificFieldStateAndLessonId(Long childId, Lesson lesson) {
        Child child = childService.findByUserId(childId);
        child.setState(StateCommands.EDIT_SPECIFIC_EVENT_FIELD.getState()
                + Separator.COLON_SEPARATOR + lesson.getId());
        child.setIsSkip(false);
        childService.create(child);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
