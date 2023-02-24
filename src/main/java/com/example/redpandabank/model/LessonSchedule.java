package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name = "lessons_schedule")
public class LessonSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long lessonScheduleId;
    LocalTime lessonStartTime;
    String day;
}
