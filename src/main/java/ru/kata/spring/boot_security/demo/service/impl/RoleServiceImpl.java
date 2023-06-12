package ru.kata.spring.boot_security.demo.service.impl;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    final
    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void remove(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> find(long id) {
        return roleRepository.findById(id);
    }
}
