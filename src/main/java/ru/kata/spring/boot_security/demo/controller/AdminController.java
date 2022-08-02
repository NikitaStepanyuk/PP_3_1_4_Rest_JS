package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

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
    public String showUsersList(Model model){
        model.addAttribute("usersList", userService.readAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roleList", roleService.findAllRole());
        return "create";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("listRoles") String[] newRoles) {
        for (String newRole : newRoles) {
            user.addRole(roleService.getRoleByName(newRole));
        }
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("user", userService.readUser(id));
        return "update";
    }

    @PatchMapping("update/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("listRoles") String[] newRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String newRole : newRoles) {
            roleSet.add(roleService.getRoleByName(newRole));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}