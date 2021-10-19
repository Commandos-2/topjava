package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository=MealsUtil.meals.stream().collect(Collectors.toMap(Meal::getId, item -> item));
    }

    @Override
    public Meal save(Meal meal,int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id,int userId) {
        Meal meal=repository.get(id);
        if(meal.getUserId().equals(userId)){
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id,int userId) {
        Meal meal=repository.get(id);
        return (meal.getUserId().equals(userId))?meal:null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(x->x.getUserId().equals(userId)).sorted(((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))).collect(Collectors.toList());
    }
}

