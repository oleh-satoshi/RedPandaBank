package com.example.redpandabank.service;

import com.example.redpandabank.model.Translate;

public interface TranslateService {
    String getBySlug(String slug);

    Translate save(Translate translate);
}
