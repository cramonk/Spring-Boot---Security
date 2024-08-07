package ru.cramonk.spring.boot_security.service;

import org.springframework.stereotype.Service;
import ru.cramonk.spring.boot_security.entity.Role;
import ru.cramonk.spring.boot_security.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String role) {
        return roleRepository.findByName(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
