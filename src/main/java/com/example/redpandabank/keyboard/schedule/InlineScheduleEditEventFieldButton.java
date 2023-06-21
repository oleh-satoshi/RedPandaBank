package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.Separator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEventFieldButton
        implements PressableWithArgument<ReplyKeyboard, Lesson> {
    final TranslateService translateService;
    final String LESSON_NAME = "lesson-name";
    final String TEACHER_NAME = "teacher-name";
    final String LESSON_START_TIME = "lesson-start-time";
    final String LESSON_DURATION = "lesson-duration";
    final String BACK = "back";

    public InlineScheduleEditEventFieldButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(LESSON_NAME) + lesson.getTitle(),
                        StateCommands.EDIT_SPECIFIC_EVENT_FIELD.getState()
                                + Separator.COLON_SEPARATOR + lesson.getId())
                .endRow()
                .row()
                .button(translateService.getBySlug(TEACHER_NAME) + lesson.getTeacher(),
                        Commands.EDIT_SCHEDULE_EVENT_TEACHER.getName()
                                + Separator.COLON_SEPARATOR + lesson.getId())
                .endRow()
                .row()
                .button(translateService.getBySlug(LESSON_START_TIME) + getStartTime(lesson),
                        Commands.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName()
                                + Separator.COLON_SEPARATOR + lesson.getId())
                .endRow()
                .row()
                .button(translateService.getBySlug(LESSON_DURATION) + lesson.getDuration(),
                        Commands.EDIT_SCHEDULE_EVENT_DURATION.getName()
                                + Separator.COLON_SEPARATOR + lesson.getId())
                .endRow()
                .row()
                .button(translateService.getBySlug(BACK),
                        Commands.EDIT_SCHEDULE_EXISTING_EVENT.getName())
                .endRow()
                .build();
    }

    public String getStartTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> lessonSchedule.getLessonStartTime()
                        + "(" + lessonSchedule.getDay().substring(0, 3) + ")")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1);
    }
}
