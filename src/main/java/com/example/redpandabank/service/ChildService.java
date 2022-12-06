package com.example.redpandabank.service;

import com.example.redpandabank.model.Child;

public interface ChildService {
    Child create(Child child);

    Child getById(Long id);

    void deleteById(Long id);
}
