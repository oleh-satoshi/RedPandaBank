package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
@Table(name = "children")
public class Child {
    @Id
    Long userId;
    BigDecimal count;
    Integer rating;
    Integer completeTask;
    Integer incompleteTask;
    String state;
    Boolean isSkip;
    String language;
}