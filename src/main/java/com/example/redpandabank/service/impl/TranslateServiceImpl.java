package com.example.redpandabank.service.impl;

import com.example.redpandabank.repository.TranslateRepository;
import com.example.redpandabank.service.TranslateService;
import org.springframework.stereotype.Component;

@Component
public class TranslateServiceImpl implements TranslateService {
    TranslateRepository translateRepository;

    public TranslateServiceImpl(TranslateRepository translateRepository) {
        this.translateRepository = translateRepository;
    }

    @Override
    public String getBySlug(String slug) {
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println("--------------------");
        String result = translateRepository.getBySlug(slug).getValue();
        System.out.println("Translate object: " + result);
        return result == null ? "result is null" : result;
    }
}
