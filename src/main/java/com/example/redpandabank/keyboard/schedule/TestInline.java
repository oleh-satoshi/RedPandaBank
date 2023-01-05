package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TestInline {
    public synchronized void sendInlineKeyboardMessage(long chat_id, String gameTitle) {
        SendMessage keyboard = InlineKeyboardMarkupBuilder.create(chat_id)
                .setText("Вы может узнать следующую информацию об игре " + gameTitle)
                .row()
                .button("Стоимость " + "\uD83D\uDCB0", "/price " + gameTitle)
                .button("Обновлено " + "\uD83D\uDDD3", "/updated " + gameTitle)
                .button("Версия " + "\uD83D\uDEE0", "/version " + gameTitle)
                .endRow()
                .row()
                .button("Требования " + "\uD83D\uDCF5", "/requirements " + gameTitle)
                .button("Покупки " + "\uD83D\uDED2", "/iap " + gameTitle)
                .button("Размер " + "\uD83D\uDD0E", "/size " + gameTitle)
                .endRow()
                .row()
                .button("Получить всю информацию об игре" + "\uD83D\uDD79", "/all " + gameTitle)
                .endRow()
                .row()
                .button("Скрыть клавиатуру", "close")
                .endRow()
                .build();
    }
}
