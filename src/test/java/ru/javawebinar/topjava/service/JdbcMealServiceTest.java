package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.*;

@ActiveProfiles(value = JDBC)
public class JdbcMealServiceTest extends MealServiceTest {
}
