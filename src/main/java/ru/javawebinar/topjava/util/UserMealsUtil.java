package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> separateMealsByDay = new HashMap<>();
        for (UserMeal items : meals) {
            separateMealsByDay.merge(items.getDateTime().toLocalDate(), items.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> selectionMeals = new ArrayList<>();
        for (UserMeal items : meals) {
            if (isBetweenHalfOpen(items.getDateTime().toLocalTime(), startTime, endTime)) {
                selectionMeals.add(convertUserMealTOUserMealWithExcess(items, separateMealsByDay.get(items.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return selectionMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> separateMealsByDay = meals.stream().collect(groupingBy(x -> x.getDateTime().toLocalDate(), summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(um -> isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> convertUserMealTOUserMealWithExcess(um, separateMealsByDay.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess convertUserMealTOUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}
