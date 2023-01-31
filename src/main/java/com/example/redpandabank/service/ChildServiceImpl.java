package com.example.redpandabank.service;

import com.example.redpandabank.model.Child;
import com.example.redpandabank.repository.ChildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ChildServiceImpl implements ChildService {
    private final static Integer ZERO = 0;
    private final static String SEPARATOR = ":";

    private ChildRepository childRepository;

    public ChildServiceImpl(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Override
    public Child create(Child child) {
        return childRepository.save(child);
    }

    @Override
    public Optional<Child> getById(Long id) {
        return childRepository.findById(id);
    }

    @Override
    public Optional<Child> findById(Long id) {
        return childRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        childRepository.deleteById(id);
    }

    @Override
    public Child createChild(Long userId) {
        Child child = new Child();
        child.setUserId(userId);
        child.setCount(BigDecimal.ZERO);
        child.setRating(ZERO);
        child.setCompleteTask(ZERO);
        child.setIncompleteTask(ZERO);
        child.setState(NO_STATE);
        child.setIsSkip(false);
        return create(child);
    }

    @Override
    public Child findByUserId(Long userId) {
        return childRepository.findByUserId(userId);
    }

    @Override
    public Long parseId(String command) {
        String[] split = command.split(SEPARATOR);
        if (split.length > 1) {
            return Long.valueOf(split[1]);
        }
        return null;
    }
}
