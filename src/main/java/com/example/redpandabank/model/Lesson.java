package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;
    private String title;
    private String teacher;
    private Integer duration;
    private Long childId;
    @OneToMany
    @JoinColumn(name = "lesson_id", nullable = false)
    private List<LessonSchedule> lessonSchedules;
}
