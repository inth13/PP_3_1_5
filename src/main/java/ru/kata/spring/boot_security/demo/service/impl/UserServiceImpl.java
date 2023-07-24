package ru.kata.spring.boot_security.demo.service.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exceptions.NotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void remove(long id) {
        findById(id);
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

    @Override
    public User update(long id, User user) {
        Optional<User> optionalUser = findById(id);

        User existingUser = optionalUser.get();
        existingUser.setPassword(user.getPassword());

        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());

        Set<Role> newRoles = user.getRoles();
        if (newRoles != null && !newRoles.isEmpty()) {
            existingUser.getRoles().clear();
            for (Role role : newRoles) {
                existingUser.getRoles().add(roleService.findByName(role.getName()));
            }
        }
        return save(existingUser);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }

    public boolean IsVacant(String userName) {
        return userRepository.findByEmail(userName) == null;
    }
}
