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
public class InlineScheduleEditEvenTeacherName implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ENTER_TEACHER_NAME = "enter-teacher-name";

    public InlineScheduleEditEvenTeacherName(LessonService lessonService,
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
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Long lessonId = parseId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        setEditTeacherNameStateAndIsSkipForUser(userId, lesson);
        String response = getResponse(lesson);
        return messageSender.sendEditMessage(userId, messageId, response);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(ENTER_TEACHER_NAME)
                + " <i>\"" + lesson.getTitle() + "\"</i> !";
    }

    private void setEditTeacherNameStateAndIsSkipForUser(Long userId, Lesson lesson) {
        Child child = childService.findByUserId(userId);
        child.setState(StateCommands.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getState()
                + Separator.COLON_SEPARATOR + lesson.getId());
        child.setIsSkip(false);
        childService.create(child);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
