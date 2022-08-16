package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepository {

    void createUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User readUser(Long id);

    List<User> readAllUsers();

    User findByUsername(String username);

}