package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleDeleteSpecificEventStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final TranslateService translateService;
    final String CHOOSE_TIME_TO_REMOVE = "choose-time-to-remove";
    final MessageSender messageSender;

    public InlineScheduleDeleteSpecificEventStartTime(LessonService lessonService,
                                                      TranslateService translateService,
                                                      MessageSender messageSender) {
        this.lessonService = lessonService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        String title = parseData(UpdateInfo.getText(update));
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LocalTime> timeList = lesson.getLessonSchedules().stream()
                .map(LessonSchedule::getLessonStartTime)
                .collect(Collectors.toList());
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (LocalTime localTime : timeList) {
            builder.row()
                    .button(localTime.toString(),
                            Commands.DELETE_SPECIFIC_EVENT_START_TIME_2.getName()
                            + Separator.COLON_SEPARATOR + localTime
                                    + Separator.COLON_SEPARATOR + lesson.getId())
                    .endRow();
        }
        InlineKeyboardMarkup inline = builder.build();
        String response = translateService.getBySlug(CHOOSE_TIME_TO_REMOVE);
        return messageSender.sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private String parseData(String data) {
        return data.split(Separator.QUOTE_SEPARATOR)[1];
    }

}
