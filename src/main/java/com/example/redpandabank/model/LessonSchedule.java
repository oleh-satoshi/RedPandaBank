package com.example.redpandabank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lessons_schedule")
public class LessonSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long lessonScheduleId;
    LocalTime lessonStartTime;
    String day;
}
