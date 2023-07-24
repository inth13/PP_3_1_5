package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptions.UserAlreadyExistException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.impl.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/admin/user")
public class AdminRestController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public AdminRestController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User user) throws UserAlreadyExistException {
        if (!userService.IsVacant(user.getUsername())) {
            throw new UserAlreadyExistException("User with the same name already exists");
        }
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> userById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        if (!userService.IsVacant(user.getUsername())) {
            throw new UserAlreadyExistException("User with the same email already exists");
        }
        return ResponseEntity.ok(userService.update(id, user));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}

