package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to user_id
    Meal save(Meal meal, int userId);

    // false if meal do not belong to user_id
    boolean delete(int id, int userId);

    // null if meal do not belong to user_id
    Meal get(int id, int userId);

    // ORDERED date_time desc
    List<Meal> getAll(int userId);

    // ORDERED date_time desc
    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
