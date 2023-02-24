package com.example.redpandabank.service.impl;

import com.example.redpandabank.model.Translate;
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
        return translateRepository.getBySlug(slug).getValue();
    }
}
