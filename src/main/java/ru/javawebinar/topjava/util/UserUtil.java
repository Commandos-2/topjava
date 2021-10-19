package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.model.Role.ADMIN;
import static ru.javawebinar.topjava.model.Role.USER;

public class UserUtil {
    public static final List<User> users = new ArrayList<>();

    public UserUtil() {
        users.add(new User(0, "Sergey", "sergkurs2006@mail.ru", "123", USER));
        users.add(new User(1, "Grigory", "grigory@mail.ru", "234", ADMIN));
    }
}
