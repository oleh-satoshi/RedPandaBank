package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.schedule.InlineScheduleEditSpecificEventStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEventLessonStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final InlineScheduleEditSpecificEventStartTimeButton
            inlineScheduleEditSpecificEventStartTimeButton;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String WHAT_TIME_FOR_LESSON = "what-time-for-lesson";
    final String YOU_WANT_TO_CHANGE = "you-want-to-change";

    public InlineScheduleEditEventLessonStartTime(LessonService lessonService,
                                                  ChildService childService,
                                                  InlineScheduleEditSpecificEventStartTimeButton
                                                          inlineScheduleEditSpecificEventStartTimeButton,
                                                  TranslateService translateService,
                                                  MessageSender messageSender) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineScheduleEditSpecificEventStartTimeButton
                = inlineScheduleEditSpecificEventStartTimeButton;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        String title = parseData(UpdateInfo.getData(update));
        Lesson lesson = lessonService.findLessonByTitle(userId, title);
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        InlineKeyboardMarkup inline = getInlineKeyboardMarkup(lessonSchedules, lesson);
        setEditStartTimeStateAndIsSkip(userId);
        String content = getResponse(lesson);
        return messageSender.sendEditMessageWithInline(userId, messageId, inline, content);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(WHAT_TIME_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i> "
                + translateService.getBySlug(YOU_WANT_TO_CHANGE);
    }

    private void setEditStartTimeStateAndIsSkip(Long childId) {
        Child child = childService.findByUserId(childId);
        child.setState(StateCommands.EDIT_SPECIFIC_EVENT_START_TIME.getState());
        child.setIsSkip(false);
        childService.create(child);
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup(List<LessonSchedule> lessonSchedules, Lesson lesson) {
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (LessonSchedule lessonSchedule : lessonSchedules) {
            builder.row()
                    .button(lessonSchedule.getLessonStartTime()
                                    + " " + lessonSchedule.getDay(),
                            StateCommands.EDIT_SPECIFIC_EVENT_START_TIME.getState()
                                    + Separator.QUOTE_SEPARATOR + lesson.getTitle()
                                    + Separator.QUOTE_SEPARATOR + lessonSchedule.getLessonStartTime())
                    .endRow();
        }
        InlineKeyboardMarkup inline = builder.build();
        return inline;
    }

    private String parseData(String command) {
        return command.split(Separator.COLON_SEPARATOR)[1];
    }
}
