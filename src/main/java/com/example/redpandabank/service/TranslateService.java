package com.example.redpandabank.service;

import com.example.redpandabank.model.Translate;
import java.util.List;

public interface TranslateService {
    String getBySlug(String slug);

    List<Translate> findAll();
}
