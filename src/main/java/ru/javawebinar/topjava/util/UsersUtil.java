package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {


    public static final List<User> users = Arrays.asList(
            new User(null, "Имя", "123@123.ru", "123", Role.USER),
            new User(null, "Фамилия", "124@123.ru", "123", Role.USER),
            new User(null, "Отчество", "125@123.ru", "123", Role.USER),
            new User(null, "Андреев", "126@123.ru", "123", Role.USER)
    );

    public static List<User> getAll() {
        return users;
    }

}
