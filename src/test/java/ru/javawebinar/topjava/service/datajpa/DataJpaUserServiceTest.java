package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(profiles = DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserAndHisMeals() {
        User user = service.getUserAndHisMeals(USER_ID);
        List<Meal> list = user.getMeals();
        USER_MATCHER.assertMatch(user, UserTestData.user);
        MEAL_MATCHER.assertMatch(list, meals);
    }
}
