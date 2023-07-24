package ru.kata.spring.boot_security.demo.exceptions;


public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
