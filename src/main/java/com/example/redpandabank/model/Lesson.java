package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", title='" + title + '\'' +
                ", teacher='" + teacher + '\'' +
                ", duration=" + duration +
                ", childId=" + childId +
                '}';
    }
}
