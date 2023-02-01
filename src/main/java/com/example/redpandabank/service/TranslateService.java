package com.example.redpandabank.service;

import com.example.redpandabank.model.Translate;
import org.springframework.stereotype.Component;

public interface TranslateService {
    String getBySlug(String slug);
}
