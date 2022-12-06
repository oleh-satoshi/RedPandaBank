package com.example.redpandabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "children")
public class Child {
    @Id
    private Long userId;
    private BigDecimal count;
    private Integer rating;
    private Integer completeTask;
    private Integer incompleteTask;
}