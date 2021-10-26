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
    public void Get() {
        Meal meal = service.get(MEAL_ID, ADMIN);
        //Meal newMeal= MealTestData.meal; // я не понимаю почему это происходит..
        //newMeal.setId(MEAL_ID);           // если раскоментировать, то все заработает, но ведь я нечего не меняю по сути этим кодом..
        Assert.assertEquals(meal, MealTestData.meal);
    }

    @Test
    public void GetAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER2));
    }

    @Test
    public void Delete() {
        service.delete(MEAL_ID, ADMIN);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN));
    }

    @Test
    public void DeleteAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, USER2));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN));
    }

    @Test
    public void GetBetweenInclusive() {
        List<Meal> list = service.getBetweenInclusive(startDateTime, endDateTime, USER2);
        Assert.assertEquals(list, Collections.singletonList(meal3));
    }


    @Test
    public void GetAll() {
        List<Meal> all = service.getAll(USER2);
        Assert.assertEquals(all, Arrays.asList(meal2, meal3));
    }

    @Test
    public void Update() {
        service.update(getNewMeal(MEAL_ID), ADMIN);
        Assert.assertEquals(service.get(MEAL_ID, ADMIN), getNewMeal(MEAL_ID));
    }

    @Test
    public void UpdateAlienMeal() {
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
    public void Create() {
        Meal created = service.create(getNewMeal(null), 100001);
        Integer newId = created.getId();
        Meal newUser = getNewMeal(newId);
        Assert.assertEquals(created, newUser);
        Assert.assertEquals(service.get(newId, ADMIN), newUser);
    }
}