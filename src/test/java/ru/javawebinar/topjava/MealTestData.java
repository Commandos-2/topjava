package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;
    public static final int ADMIN = START_SEQ + 1;
    public static final int USER2 = START_SEQ + 2;
    public static final LocalDate startDateTime=LocalDate.of(2021, 1, 1);
    public static final LocalDate endDateTime=LocalDate.of(2021, 1, 20);
    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2020, 12, 19, 10, 0, 0), "Break", 400);
    public static final Meal meal2 = new Meal(100027, LocalDateTime.of(2021, 8, 20, 2, 0, 0), "Lunch", 900);
    public static final Meal meal3 = new Meal(100026, LocalDateTime.of(2021, 1, 10, 10, 20, 0), "Dinner", 1200);

    public static Meal getNewMeal(Integer id) {
        return new Meal(id, LocalDateTime.of(2021, 10, 25, 20, 30, 30), "Dinner", 1555);
    }
}

