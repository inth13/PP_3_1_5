package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    @GetMapping("/authUser")
    public ResponseEntity<User> getAuthUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}
