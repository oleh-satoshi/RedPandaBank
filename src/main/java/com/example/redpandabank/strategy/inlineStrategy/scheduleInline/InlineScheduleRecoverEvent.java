package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.model.Lesson;
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
public class InlineScheduleRecoverEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final TranslateService translateService;
    final String LESSON = "lesson";
    final MessageSender messageSender;
    final String BACK_TO_SCHEDULE = "back-to-schedule";
    final String RETURN_TO_SCHEDULE = "return-to-schedule";

    public InlineScheduleRecoverEvent(LessonService lessonService,
                                      TranslateService translateService,
                                      MessageSender messageSender) {
        this.lessonService = lessonService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        String command = UpdateInfo.getData(update);
        Lesson lesson = lessonService.getById(parseId(command));
        String content;
        if (lesson.getIsDeleted()) {
            lesson.setIsDeleted(false);
            lessonService.create(lesson);
            content = translateService.getBySlug(LESSON) + lesson.getTitle()
                    + translateService.getBySlug(BACK_TO_SCHEDULE);
            messageSender.sendMessageViaURL(childId,
                    content);
            messageSender.sendMessageViaURL(childId,
                    lessonService.getLessonInfoByIdForSendByUrl(lesson.getId()));
        } else {
            content = translateService.getBySlug(RETURN_TO_SCHEDULE);
            messageSender.sendMessageViaURL(childId,content);
        }
        return null;
    }

    private Long parseId(String command) {
        return Long.valueOf(command.split(Separator.COLON_SEPARATOR)[1]);
    }
}
