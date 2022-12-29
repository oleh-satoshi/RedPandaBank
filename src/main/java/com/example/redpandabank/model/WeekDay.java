package com.example.redpandabank.model;

public enum WeekDay{
        SUNDAY("Вс"),
        MONDAY("Пн"),
        TUESDAY("Вт"),
        WEDNESDAY("Ср"),
        THURSDAY("Чт"),
        FRIDAY("Пт"),
        SATURDAY("Сб"),
        ALL_WEEK("Вся неделя");

        String day;

        WeekDay(String day) {
            this.day = day;
        }

        public String getDay() {
            return day;
        }
    }