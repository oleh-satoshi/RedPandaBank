//package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;
//
//import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//public class InlineSaveTeacherName implements InlineHandler<Update> {
//    @Override
//    public BotApiMethod<?> handle(Update update) {
//        Long childId = update.getCallbackQuery().getMessage().getChatId();
//        String command = update.getCallbackQuery().getMessage().getText();
//        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
//        return null;
//
//    }
//}
