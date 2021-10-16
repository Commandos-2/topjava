package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MealListStorage implements Storage {
    private List<Meal> storage;

    public MealListStorage() {
        storage = new LinkedList(Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }

    @Override
    public boolean update(Meal newMeal) {
        for (Meal meal : storage) {
            if (meal.getUuid().equals(newMeal.getUuid())) {
                meal.setDateTime(newMeal.getDateTime());
                meal.setCalories(newMeal.getCalories());
                meal.setDescription(newMeal.getDescription());
                return true;
            }
        }
        return false;
    }

    public List<Meal> getStorage() {
        return storage;
    }

    @Override
    public void delete(String uuid) {
        for (Meal meal : storage) {
            if (meal.getUuid().equals(uuid)) {
                storage.remove(meal);
                break;
            }
        }
    }

    @Override
    public Meal get(String uuid) {
        for (Meal meal : storage) {
            if (meal.getUuid().equals(uuid)) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public void save(Meal meal) {
        storage.add(meal);
    }
}

