package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class InlineRecoverEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final static String SEPARATOR = ":";

    public InlineRecoverEvent(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        String command = update.getCallbackQuery().getData();
        Lesson lesson = lessonService.getById(parseId(command));
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        if (lesson.getIsDeleted()) {
            lesson.setIsDeleted(false);
            lessonService.create(lesson);
            new MessageSenderImpl().sendMessageViaURL(childId, "Урок " + lesson.getTitle() + " снова добавлен в твое расписание!%0A%0A" + lessonService.getInfoLessonbyId(lesson.getLessonId()));
        } else {
            new MessageSenderImpl().sendMessageViaURL(childId,"Я уже добавил этот урок в твое расписание, не переживай");
        }
        return null;
    }

    private Long parseId(String command) {
        return Long.valueOf(command.split(SEPARATOR)[1]);
    }
}
