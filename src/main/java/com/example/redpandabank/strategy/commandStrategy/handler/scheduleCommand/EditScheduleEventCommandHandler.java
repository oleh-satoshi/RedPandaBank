package com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.example.redpandabank.strategy.commandStrategy.handler.scheduleCommand.DeleteEventCommandHandler.SEPARATOR;

@PackagePrivate
@Component
public class EditScheduleEventCommandHandler implements CommandHandler<Update> {
    final LessonService lessonService;

    public EditScheduleEventCommandHandler(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getMessage().getChatId();
        List<Lesson> lessons = lessonService.findAllByChildId(childId);

        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilderImpl =
                InlineKeyboardMarkupBuilderImpl.create()
                        .row();
        for (Lesson lesson : lessons) {
            inlineKeyboardMarkupBuilderImpl.button(lesson.getTitle(),
                    Command.EDIT_SCHEDULE_EVENT_FIELD.getName() + SEPARATOR + lesson.getTitle()).endRow();
        }
        SendMessage sendMessage = new MessageSenderImpl().sendMessageWithInline(childId,
                "Какой урок ты хочешь редактировать?",
                inlineKeyboardMarkupBuilderImpl.build());
        return sendMessage;
    }
}
