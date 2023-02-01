package com.example.redpandabank.service;

import com.example.redpandabank.model.Child;

import java.util.Optional;

public interface ChildService {
    Child create(Child child);

    Optional<Child> getById(Long id);

    Optional<Child> findById(Long id);

    void deleteById(Long id);

    Child createChild(Long userId);

    Child findByUserId(Long userId);

    Optional<Long> parseId(String command);
}
