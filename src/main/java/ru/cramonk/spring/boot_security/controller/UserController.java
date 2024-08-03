package ru.cramonk.spring.boot_security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cramonk.spring.boot_security.entity.Role;
import ru.cramonk.spring.boot_security.entity.User;
import ru.cramonk.spring.boot_security.service.RegistrationService;
import ru.cramonk.spring.boot_security.service.UserService;
import ru.cramonk.spring.boot_security.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

@Controller
public class UserController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final UserValidator validator;

    public UserController(UserService userService, RegistrationService registrationService, UserValidator validator) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.validator = validator;
    }

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/registration";
        }
        Role role_user = userService.findRoleByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role_user));
        registrationService.register(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/user")
    public String getUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
    @GetMapping(value = "/admin")
    public String getAdmin(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "admin";
    }
    @GetMapping(value = "/admin/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value = "/admin/new")
    public String creationPage(@ModelAttribute("user") User user) {
        return "new_user";
    }



    @GetMapping(value = "/admin/update")
    public String updatePage(@RequestParam("username") String username, @ModelAttribute("user") User user, Model model) {
        model.addAttribute(userService.findByUsername(username));
        return "update_user";
    }


    @PostMapping(value = "/admin/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/update_user";
        }
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

}