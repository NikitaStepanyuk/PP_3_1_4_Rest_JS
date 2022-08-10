package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleServiceImpl roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void createUser(User user) {
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(roleService.getPersistRolesByRoleSet(user.getRoles()));
        userRepository.createUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = readUser(id);
        user.setRoles(new HashSet<>());
        userRepository.updateUser(user);
        userRepository.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(roleService.getPersistRolesByRoleSet(user.getRoles()));
        userRepository.updateUser(user);
    }

    @Override
    public User readUser(Long id) {
        return userRepository.readUser(id);
    }

    @Override
    public List<User> readAllUsers() {
        return userRepository.readAllUsers();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}