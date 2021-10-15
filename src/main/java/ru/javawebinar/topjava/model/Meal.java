package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Meal {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final String uuid;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.uuid= UUID.randomUUID().toString();
    }

    public Meal() {
        this.dateTime = null;
        this.description = "";
        this.calories = 0;
        this.uuid= UUID.randomUUID().toString();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public String getUuid() {
        return uuid;
    }
}
