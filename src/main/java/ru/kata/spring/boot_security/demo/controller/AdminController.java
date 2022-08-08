package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showUsersList(Principal principal, Model model){
        model.addAttribute("users", userService.readAllUsers());
        model.addAttribute("roles", roleService.findAllRole());
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "admin";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("newRoles") String[] newRoles) {
        user.setRoles(roleService.getRolesByArray(newRoles));
        userService.createUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PutMapping("update/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("userRoles") String[] newRoles) {
        user.setRoles(roleService.getRolesByArray(newRoles));
        userService.updateUser(user);
        return "redirect:/admin";
    }

}