package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.schedule.InlineEditSpecificEventStartTimeChooseOperationButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@PackagePrivate
@Component
public class InlineEditScheduleEventLessonStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final InlineEditSpecificEventStartTimeChooseOperationButton
            inlineEditSpecificEventStartTimeChooseOperationButton;

    public InlineEditScheduleEventLessonStartTime(LessonService lessonService,
                                                  ChildService childService, InlineEditSpecificEventStartTimeChooseOperationButton inlineEditSpecificEventStartTimeChooseOperationButton) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineEditSpecificEventStartTimeChooseOperationButton = inlineEditSpecificEventStartTimeChooseOperationButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseData(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LocalTime> timeList = lesson.getLessonSchedules().stream()
                .map(LessonSchedule::getLessonStartTime)
                .collect(Collectors.toList());
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (LocalTime localTime : timeList) {
            builder.row()
                    .button(localTime.toString(), Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName()
                            + LessonService.COLON_SEPARATOR + localTime.toString())
                    .endRow();
        }
        InlineKeyboardMarkup inline = builder.build();
        Child child = childService.findByUserId(childId);
        child.setState(State.EDIT_SPECIFIC_EVENT_START_TIME.getState());
        child.setIsSkip(false);
        childService.create(child);
        String content = "Какое время для урока <i>\"" + lesson.getTitle() + "\"</i> ты хочешь изменить?";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, content);


    }

    private String parseData(String command) {
        return command.split(LessonService.COLON_SEPARATOR)[1];
    }
}
