package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MealServiceTestDATAJPA.class,
        MealServiceTestJDBC.class,
        MealServiceTestJPA.class,
        UserServiceTest.class
})
public class AllServiceTest {
}
