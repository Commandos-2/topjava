package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryStorage implements MealsStorage {
    private final Map<Integer, Meal> storage;
    private final AtomicInteger counter = new AtomicInteger(0);

    public MealMemoryStorage() {
        storage = new ConcurrentHashMap();
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public List<Meal> getStorage() {
        return new ArrayList(storage.values());
    }

    @Override
    public void delete(Integer uuid) {
        storage.remove(uuid);
    }

    @Override
    public Meal get(Integer uuid) {
        return storage.get(uuid);
    }

    @Override
    public Meal save(Meal meal) throws Exception {
        if (meal.getUuid() == 0) {
            meal.setUuid(counter.incrementAndGet());
            return storage.put(meal.getUuid(), meal);
        }
        if (!storage.containsKey(meal.getUuid())) {
            throw new Exception("Meals is not exist");
        }
        return storage.put(meal.getUuid(), meal);
    }

    public AtomicInteger getCounter() {
        return counter;
    }
}
