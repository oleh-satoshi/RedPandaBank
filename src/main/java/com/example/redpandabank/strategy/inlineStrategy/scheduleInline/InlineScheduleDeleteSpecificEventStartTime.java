package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleDeleteSpecificEventStartTime implements InlineHandler<Update> {
    final LessonService lessonService;

    public InlineScheduleDeleteSpecificEventStartTime(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseData(update.getCallbackQuery().getMessage().getText());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LocalTime> timeList = lesson.getLessonSchedules().stream()
                .map(LessonSchedule::getLessonStartTime)
                .collect(Collectors.toList());
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (LocalTime localTime : timeList) {
                    builder.row()
                    .button(localTime.toString(), Command.DELETE_SPECIFIC_EVENT_START_TIME_2.getName()
                            + Separator.COLON_SEPARATOR + localTime + Separator.COLON_SEPARATOR + lesson.getLessonId())
                    .endRow();
        }
        InlineKeyboardMarkup inline = builder.build();
        String response = "Какое время ты хочешь удалить?";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private String parseData(String data) {
        return data.split(Separator.QUOTE_SEPARATOR)[1];
    }

}
