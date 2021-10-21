package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.Role.ADMIN;
import static ru.javawebinar.topjava.model.Role.USER;

public class UserUtil {
    public static final List<User> users = Arrays.asList(
            new User(null, "Sergey", "sergkurs2006@mail.ru", "123", USER),
            new User(null, "Grigory", "grigory@mail.ru", "234", ADMIN));
}
