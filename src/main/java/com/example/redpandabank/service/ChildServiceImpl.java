package com.example.redpandabank.service;

import com.example.redpandabank.model.Child;
import com.example.redpandabank.repository.ChildRepository;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl implements ChildService {
    private ChildRepository childRepository;

    public ChildServiceImpl(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Override
    public Child create(Child child) {
        return childRepository.save(child);
    }

    @Override
    public Child getById(Long id) {
        return childRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find child by id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        childRepository.deleteById(id);
    }
}
