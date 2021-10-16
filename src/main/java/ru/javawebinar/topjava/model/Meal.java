package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Meal {
    private final String uuid;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(UUID.randomUUID().toString(), dateTime, description, calories);
    }

    public Meal(String uuid, LocalDateTime dateTime, String description, int calories) {
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal() {
        this(null, "", 0);
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "uuid='" + uuid + '\'' +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
