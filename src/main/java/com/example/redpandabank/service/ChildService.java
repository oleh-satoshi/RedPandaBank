package com.example.redpandabank.service;

import com.example.redpandabank.model.Child;

import java.util.Optional;

public interface ChildService {
    Child create(Child child);

    Child getById(Long id);

    boolean findById(Long id);

    void deleteById(Long id);

    Child createChild(Long userId);
}
