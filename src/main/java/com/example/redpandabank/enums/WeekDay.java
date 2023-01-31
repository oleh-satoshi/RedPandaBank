package com.example.redpandabank.enums;

public enum WeekDay{
        SUNDAY("Sunday"),
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday"),
        ALL_WEEK("All eww");

        String day;

        WeekDay(String day) {
            this.day = day;
        }

        public String getDay() {
            return day;
        }
    }