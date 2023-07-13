package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.impl.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/admin/user")
public class AdminRestController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminRestController(UserServiceImpl userService, RoleServiceImpl roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> userById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = optionalUser.get();
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setEmail(user.getEmail());
        existingUser.setLastName(user.getLastName());
        existingUser.setUsername(user.getUsername());
        existingUser.setAge(user.getAge());

        Set<Role> newRoles = user.getRoles();
        if (newRoles != null && !newRoles.isEmpty()) {
            existingUser.getRoles().clear();
            for (Role role : newRoles) {
                existingUser.getRoles().add(roleService.findByName(role.getName()));
            }
        }

        return ResponseEntity.ok(userService.save(existingUser));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}

