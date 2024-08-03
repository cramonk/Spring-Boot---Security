package ru.cramonk.spring.boot_security.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.cramonk.spring.boot_security.entity.User;
import ru.cramonk.spring.boot_security.service.UserService;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "","Человек с таким именем пользователя существует");
        }
    }
}
