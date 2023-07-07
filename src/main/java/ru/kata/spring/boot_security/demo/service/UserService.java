package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void save(User user);

    void remove(long id);

    User find(long id);

    User getUserByEmail(String name);
}
