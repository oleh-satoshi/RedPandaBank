package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;

import java.util.List;

@Data
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long lessonId;
    String title;
    String teacher;
    Integer duration;
    Long childId;
    @OneToMany
    @JoinColumn(name = "lesson_id", nullable = false)
    List<LessonSchedule> lessonSchedules;
    Boolean isDeleted;

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
