package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
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
public class InlineScheduleAddTeacherName implements InlineHandler<Update> {
    final ChildService childService;
    final LessonService lessonService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String WHO_TEACHES = "who-teaches";

    public InlineScheduleAddTeacherName(ChildService childService,
                                        LessonService lessonService,
                                        TranslateService translateService,
                                        MessageSender messageSender) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        String text = UpdateInfo.getData(update);
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        Lesson lesson = lessonService.getById(parseId(text));
        response = getResponseBySlug(child, lesson);
        child.setState(StateCommands.SAVE_EVENT_TEACHER_NAME.getState());
        childService.create(child);
        return messageSender.sendEditMessage(userId, messageId, response);
    }

    private String getResponseBySlug(Child child, Lesson lesson) {
        return translateService.getBySlug(WHO_TEACHES)
                + " <i>\"" + lesson.getTitle() + " \"</i>?";
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }

}
