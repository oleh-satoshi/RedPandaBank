package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Long childId;
    private LocalTime lessonsStartTime;
    private String weekDay;
}
