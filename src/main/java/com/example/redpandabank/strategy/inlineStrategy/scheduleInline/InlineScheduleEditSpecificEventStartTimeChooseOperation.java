package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.schedule.InlineScheduleEditSpecificEventStartTimeChooseOperationButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditSpecificEventStartTimeChooseOperation implements InlineHandler<Update> {
    final InlineScheduleEditSpecificEventStartTimeChooseOperationButton chooseOperationButton;
    final LessonService lessonService;


    public InlineScheduleEditSpecificEventStartTimeChooseOperation(InlineScheduleEditSpecificEventStartTimeChooseOperationButton chooseOperationButton,
                                                                   LessonService lessonService) {
        this.chooseOperationButton = chooseOperationButton;
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Long lessonId = parseId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        String response = "Что ты хочешь сделать со временем начала урока <i>\"" + lesson.getTitle() + "\"</i> !";
        InlineKeyboardMarkup inline = chooseOperationButton.getKeyboard(lesson);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
