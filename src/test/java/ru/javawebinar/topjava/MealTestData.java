package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2020, 12, 19, 10, 00, 00), "Break", 400);

    public static Meal getNewMeal(Integer id) {
        return new Meal(id, LocalDateTime.of(2021, 10, 25, 20, 30, 30), "Dinner", 1555);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(expected);
    }

    public static List<Meal> getListMeal() {
        List<Meal> list = new ArrayList<>();
        list.add(new Meal(100003, LocalDateTime.of(2021, 8, 20, 02, 00, 00), "Lunch", 900));
        list.add(new Meal(100002, LocalDateTime.of(2021, 01, 10, 10, 20, 00), "Dinner", 1200));
        return list;
    }
}

