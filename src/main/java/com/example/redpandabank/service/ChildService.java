package com.example.redpandabank.service;

import com.example.redpandabank.model.Child;

import java.util.Optional;

public interface ChildService {
    String NO_STATE = "no state";
    Child create(Child child);

    Child getById(Long id);

    boolean findById(Long id);

    void deleteById(Long id);

    Child createChild(Long userId);

    Child findByUserId(Long userId);

    Long parseId(String command);
}
