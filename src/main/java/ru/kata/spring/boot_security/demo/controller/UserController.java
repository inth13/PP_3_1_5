package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userServiceImpl;

    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/user")
    public String showAllUser(ModelMap modelMap, Principal principal) {
        User userByName = userServiceImpl.getUserByName(principal.getName());
        modelMap.addAttribute("user", userByName);
        return "user/userInfo";
    }
}
