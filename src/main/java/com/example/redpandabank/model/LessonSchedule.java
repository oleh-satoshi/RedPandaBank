package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;

import java.time.LocalTime;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)@Entity
@Table(name = "lessons_schedule")
public class LessonSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long LessonScheduleId;
    Long childId;
    LocalTime lessonStartTime;
    String day;
}
