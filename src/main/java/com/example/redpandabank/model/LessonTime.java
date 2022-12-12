package com.example.redpandabank.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "lessons_time")
public class LessonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime lessonsStartTime;
    private LocalTime lessonsFinishTime;
    private LocalTime notificationsTime;
    @Enumerated(value = EnumType.STRING)
    private WeekDay weekDay;

    public enum WeekDay{
        SUNDAY("Вс"),
        MONDAY("Пн"),
        TUESDAY("Вт"),
        WEDNESDAY("Ср"),
        THURSDAY("Чт"),
        FRIDAY("Пт"),
        SATURDAY("Сб");

        String day;

        WeekDay(String day) {
            this.day = day;
        }

        public String getDay() {
            return day;
        }
    }
}
