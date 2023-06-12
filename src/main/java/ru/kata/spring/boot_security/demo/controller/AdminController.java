package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userServiceImpl;

    public AdminController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public String showAllUser(ModelMap modelMap) {
        List<User> allUsers = userServiceImpl.getAllUsers();
        modelMap.addAttribute("users", allUsers);
        return "user/users";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "user/new";
    }

    @PostMapping("/new")
    public String addPerson(@ModelAttribute("user") User user) {
        userServiceImpl.save(user);
        return "redirect:/admin/";
    }

    @PatchMapping("/update")
    public String create(@ModelAttribute("user") User user) {
        userServiceImpl.save(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable("id") long id) {
        userServiceImpl.remove(id);
        return "redirect:/admin/";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") long id) {
        User user = userServiceImpl.find(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
}
