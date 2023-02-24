package com.example.redpandabank.service;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.repository.ChildRepository;
import com.example.redpandabank.util.Separator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ChildServiceImpl implements ChildService {
    private final static Integer ZERO = 0;

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
        child.setState(State.NO_STATE.getState());
        child.setIsSkip(false);
        return create(child);
    }

    @Override
    public Child findByUserId(Long userId) {
        return childRepository.findByUserId(userId);
    }

    @Override
    public Optional<Long> parseId(String command) {
        String[] split = command.split(Separator.COLON_SEPARATOR);
        if (split.length > 1) {
            return Optional.of(Long.valueOf(split[1]));
        }
        return Optional.empty();
    }
}
