package com.example.redpandabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
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
        return "Lesson{"
                + "lessonId =" + id
                + ", title ='" + title + '\''
                + ", teacher ='" + teacher + '\''
                + ", duration =" + duration
                + ", childId =" + childId
                + '}';
    }
}
