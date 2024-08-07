package ru.cramonk.spring.boot_security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.cramonk.spring.boot_security.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(User user);
    void updateUser(User user);
    User findByUsername(String username);
    List<User> findAll();
    void deleteById(Long id);

}
