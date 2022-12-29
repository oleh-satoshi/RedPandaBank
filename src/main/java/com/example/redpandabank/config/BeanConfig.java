package com.example.redpandabank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class BeanConfig {
    @Bean
    public CallbackQuery getCallbackQuery() {
        return new CallbackQuery();
    }    
}
