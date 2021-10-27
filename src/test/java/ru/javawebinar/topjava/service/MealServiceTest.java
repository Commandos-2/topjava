package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, ADMIN);
        Meal newMeal = MealTestData.meal; // я не понимаю почему это происходит..
        newMeal.setId(MEAL_ID);           // если раскоментировать, то все заработает, но ведь я нечего не меняю по сути этим кодом..
        Assert.assertEquals(MealTestData.meal, meal);
    }

    @Test
    public void getAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER2));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, ADMIN);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN));
    }

    @Test
    public void deleteAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, USER2));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> list = service.getBetweenInclusive(startDateTime, endDateTime, USER2);
        Assert.assertEquals(Collections.singletonList(meal2), list);
    }


    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER2);
        Assert.assertEquals(Arrays.asList(meal3, meal2), all);
    }

    @Test
    public void update() {
        service.update(getNewMeal(MEAL_ID), ADMIN);
        Assert.assertEquals(getNewMeal(MEAL_ID), service.get(MEAL_ID, ADMIN));
    }

    @Test
    public void updateAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.update(getNewMeal(MEAL_ID), USER2));
    }

    @Test
    public void duplicateDateCreate() {
        Meal duplicateDateMeal = meal2;
        duplicateDateMeal.setDateTime(service.get(MEAL_ID, ADMIN).getDateTime());
        duplicateDateMeal.setId(null);
        assertThrows(DataAccessException.class, () ->
                service.create(duplicateDateMeal, ADMIN));
    }

    @Test
    public void create() {
        Meal created = service.create(getNewMeal(null), ADMIN);
        Integer newId = created.getId();
        Meal newUser = getNewMeal(newId);
        Assert.assertEquals(newUser, created);
        Assert.assertEquals(newUser, service.get(newId, ADMIN));
    }
}