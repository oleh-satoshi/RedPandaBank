package com.example.redpandabank.repository;

import com.example.redpandabank.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Child findByUserId(Long userId);
}
