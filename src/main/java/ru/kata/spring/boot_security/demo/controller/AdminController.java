package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userServiceImpl;
    private final RoleService roleServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userServiceImpl, RoleService roleServiceImpl, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showAllUser(ModelMap modelMap, Principal principal) {
        User userByName = userServiceImpl.getUserByName(principal.getName());

        List<User> allUsers = userServiceImpl.getAllUsers();
        modelMap.addAttribute("users", allUsers);
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("listOfRoles", roleServiceImpl.getAllRoles());
        modelMap.addAttribute("userEmail", userByName.getEmail());
        modelMap.addAttribute("userRoles", userByName.getRolesAsString());
        modelMap.addAttribute("isAdmin", userByName.isAdmin());
        modelMap.addAttribute("currentUser", userByName);

        return "bootstrap/users";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "bootstrap/new";
    }

    @PostMapping("/new")
    public String addPerson(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userServiceImpl.save(user);
        return "redirect:/admin/";
    }

    @PatchMapping("/update")
    public String create(@ModelAttribute("user") User user) {
        User userById = userServiceImpl.find(user.getId());
        userById.setFirstName(user.getFirstName());
        userById.setLastName(user.getLastName());
        userById.setAge(user.getAge());
        userById.setPassword(passwordEncoder.encode(user.getPassword()));
        userById.setEmail(user.getEmail());
        userById.setRoles(user.getRoles());
        userServiceImpl.save(userById);
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
        return "bootstrap/edit";
    }
}
