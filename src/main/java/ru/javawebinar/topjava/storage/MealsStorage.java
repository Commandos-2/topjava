package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsStorage {
    Meal save(Meal meal) throws Exception;

    Meal get(Integer uuid);

    void delete(Integer uuid);

    List<Meal> getStorage();
}
