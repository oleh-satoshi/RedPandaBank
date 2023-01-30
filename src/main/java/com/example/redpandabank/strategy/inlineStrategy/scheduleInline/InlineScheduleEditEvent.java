package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@PackagePrivate
@Component
public class InlineScheduleEditEvent implements InlineHandler<Update> {
    final LessonService lessonService;

    public InlineScheduleEditEvent(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        List<Lesson> lessonsSet = lessonService.findAllByChildIdWithoutLessonSchedule(childId);
        List<Lesson> lessons = new ArrayList<>(lessonsSet);
        String lessonTitle;
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (Lesson lesson : lessons) {
            builder.row();
            builder.button(lesson.getTitle(), Command.EDIT_SPECIFIC_EXISTING_EVENT.getName()
                    + Separator.COLON_SEPARATOR + lesson.getLessonId());
            builder.endRow();
        }
        InlineKeyboardMarkup keyboard = builder.build();
        String response = "Какой урок ты хочешь изменить?";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, keyboard, response);
    }
}
