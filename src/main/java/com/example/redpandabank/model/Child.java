package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "children")
public class Child {
    @Id
    private Long userId;
    private BigDecimal count;
    private Integer rating;
    private Integer completeTask;
    private Integer incompleteTask;
    String state;
    Boolean isSkip;
}