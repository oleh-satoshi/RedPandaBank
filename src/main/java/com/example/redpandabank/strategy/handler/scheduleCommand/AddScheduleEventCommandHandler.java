package com.example.redpandabank.strategy.handler.scheduleCommand;

import com.example.redpandabank.model.Command;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.strategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.security.PrivateKey;
import java.time.LocalDateTime;

@Component
public class AddScheduleEventCommandHandler implements CommandHandler {
    private final MessageSender messageSender;
    private String title;
    private String description;
    private LocalDateTime startTime;

    public AddScheduleEventCommandHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage;
        String response;
        Long userId = update.getMessage().getChatId();
        String text = update.getMessage().getText();;

        if (text.contains(Command.ADD_SCHEDULE_EVENT.getName())) {
            response = "Напиши название урока, то что напишешь мы сохраним!";
            messageSender.sendToTelegram(userId, response);
            sendMessage = SendMessage.builder()
                    .chatId(userId)
                    .text(response)
                    .build();
            return sendMessage;
        } else if (text.contains(Command.SAVE_EVENT_NAME.getName())) {
            title = text.substring(4);
            System.out.println(title);
            response = "Отлично! Мы сохранили название урока, можем идти дальше!\n "
                    + "Если для тебя это новый урок и его название тебе ничего говорит, "
                    + "то можешь добавить описание урока, например: \n"
                    + "<i>\"На этом уроке мы учим историю Мира\"</i>";
            sendMessage = SendMessage.builder()
                    .text(response)
                    .parseMode("HTML")
                    .chatId(userId)
                    .build();
            return sendMessage;
        } else if (text.contains(Command.SAVE_EVENT_DESCRIPTION.getName())) {
            description = text.substring(9);
            System.out.println(description);
            response = "Отлично! Мы сохранили описание урока, можем идти дальше!\n "
                    + "Если для тебя это новый урок и его название тебе ничего говорит, "
                    + "то можешь добавить описание урока, например: \n"
                    + "<i>\"На этом уроке мы учим историю Мира\"</i>";
            sendMessage = SendMessage.builder()
                    .text(response)
                    .parseMode("HTML")
                    .chatId(userId)
                    .build();
            return sendMessage;
        }

//        response = "Давай добавим урок!";
//        sendMessage = SendMessage.builder()
//                .text(response)
//                .chatId(userId)
//                .replyMarkup(emptyButton.getEmptyMenuButton())
//                .build();
//        return sendMessage;
        return null;
    }

    private Lesson createLesson(String title, String description, LocalDateTime startTime) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setLessonsStartTime(startTime);
        return lesson;
    }
}
