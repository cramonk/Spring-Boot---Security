package ru.cramonk.spring.boot_security.service;

import ru.cramonk.spring.boot_security.entity.Role;

import java.util.List;

public interface RoleService {
    Role findRoleByName(String role);
    List<Role> findAll();
}
