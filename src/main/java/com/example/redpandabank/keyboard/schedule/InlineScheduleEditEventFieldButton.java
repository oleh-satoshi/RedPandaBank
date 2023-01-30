package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.util.Separator;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;
import java.util.stream.Collectors;

@PackagePrivate
@Component
public class InlineScheduleEditEventFieldButton implements
        PressableWithArgument<ReplyKeyboard, Lesson> {
    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Название урока: " + lesson.getTitle(),
                        Command.EDIT_SPECIFIC_EVENT_FIELD.getName()
                                + Separator.COLON_SEPARATOR + lesson.getLessonId())
                .endRow()
                .row()
                .button("Имя учителя: " + lesson.getTeacher(),
                        Command.EDIT_SCHEDULE_EVENT_TEACHER.getName()
                                + Separator.COLON_SEPARATOR + lesson.getLessonId())
                .endRow()
                .row()
                .button("Время начала урока: " + getStartTime(lesson),
                        Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName()
                                + Separator.COLON_SEPARATOR + lesson.getLessonId())
                .endRow()
                .row()
                .button("Длительность урока: " + lesson.getDuration(),
                        Command.EDIT_SCHEDULE_EVENT_DURATION.getName()
                                + Separator.COLON_SEPARATOR + lesson.getLessonId())
                .endRow()
                .build();
    }

    public String getStartTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> lessonSchedule.getLessonStartTime() + " ")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1);
    }
}
