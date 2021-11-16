package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(profiles = DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealAndUser() {
        Meal meal=service.getMealAndUser(MEAL1_ID,USER_ID);
        User user=meal.getUser();
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }
}
