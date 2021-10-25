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

import java.time.LocalDateTime;
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
    public void testGet() {
        Meal meal = service.get(MEAL_ID, 100001);
        Assert.assertEquals(meal, MealTestData.meal);
    }

    @Test
    public void testGetAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, 100002));
    }

    @Test
    public void testDelete() {
        service.delete(MEAL_ID, 100001);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, 100001));
    }

    @Test
    public void testDeleteAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, 100002));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, 100001));
    }

    @Test
    public void testGetBetweenInclusive() {
    }

    @Test
    public void testGetAll() {
        List<Meal> all = service.getAll(100002);
        assertMatch(all, getListMeal());
    }

    @Test
    public void testUpdate() {
        Meal updated = getNewMeal(MEAL_ID);
        service.update(updated, 100001);
        Assert.assertEquals(service.get(MEAL_ID, 100001), updated);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2020, 12, 19, 10, 00, 00), "Dinner", 1000), 100001));
    }

    @Test
    public void testCreate() {
        Meal created = service.create(getNewMeal(null), 100001);
        Integer newId = created.getId();
        Meal newUser = getNewMeal(22);
        newUser.setId(newId);
        Assert.assertEquals(created, newUser);
        Assert.assertEquals(service.get(newId, 100001), newUser);
    }
}