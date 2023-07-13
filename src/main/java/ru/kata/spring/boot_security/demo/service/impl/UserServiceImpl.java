package ru.kata.spring.boot_security.demo.service.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exceptions.NotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void remove(long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User find(long id) {
        return userRepository.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }


}
