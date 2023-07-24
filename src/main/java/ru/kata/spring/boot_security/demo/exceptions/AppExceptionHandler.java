package ru.kata.spring.boot_security.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        HttpStatus conflictStatus = HttpStatus.CONFLICT;
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), conflictStatus, ZonedDateTime.now());

        return new ResponseEntity<>(errorMessage, conflictStatus);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleUserAlreadyExistException(NotFoundException ex) {
        HttpStatus conflictStatus = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), conflictStatus, ZonedDateTime.now());

        return new ResponseEntity<>(errorMessage, conflictStatus);
    }
}
