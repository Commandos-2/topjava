package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealToList {
    private List<MealTo> mealToList;
    final int caloriesPerDay = 2000;

    public MealToList(LocalTime startTime, LocalTime endTime) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        this.mealToList = filteredByStreams(meals, startTime, endTime, caloriesPerDay);
    }

    public List<MealTo> getMealToList() {
        return mealToList;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void delete(String uuid) {
        for (MealTo mealTo : mealToList) {
            if (mealTo.getUuid().equals(uuid)) {
                mealToList.remove(mealTo);
                break;
            }
        }
    }

    public MealTo get(String uuid) {
        for (MealTo mealTo : mealToList) {
            if (mealTo.getUuid().equals(uuid)) {
                return mealTo;
            }
        }
        return null;
    }

    public void add(MealTo mealTo) {
        mealToList.add(mealTo);
    }

    public void RecalculateСaloriesPerDay() {
        Map<LocalDate, Integer> caloriesSumByDate = mealToList.stream()
                .collect(
                        Collectors.groupingBy(MealTo::getDate, Collectors.summingInt(MealTo::getCalories))
                );

        mealToList.stream()
                .peek(mealTo -> mealTo.setExcess(caloriesSumByDate.get(mealTo.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}

