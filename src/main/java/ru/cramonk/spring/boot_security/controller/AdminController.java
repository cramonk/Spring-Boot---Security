package ru.cramonk.spring.boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cramonk.spring.boot_security.entity.Role;
import ru.cramonk.spring.boot_security.entity.User;
import ru.cramonk.spring.boot_security.service.RoleService;
import ru.cramonk.spring.boot_security.service.UserService;
import ru.cramonk.spring.boot_security.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator validator;

    public AdminController(UserService userService, RoleService roleService, UserValidator validator) {
        this.userService = userService;
        this.roleService = roleService;
        this.validator = validator;
    }

    @GetMapping(value = "")
    public String getAdmin(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping(value = "/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value = "/new")
    public String creationPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles",
                roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()));
        return "new_user";
    }

    @PostMapping(value = "/new")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        List<Role> roles = new ArrayList<>();
        for (String name : user.getRoleNames()) {
            roles.add(roleService.findRoleByName(name));
        }
        user.setRoles(roles);
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles",
                    roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()));
            return "new_user";
        }
        userService.register(user);
        return "redirect:/admin/users";
    }


    @GetMapping(value = "/update")
    public String updatePage(@RequestParam("username") String username, @ModelAttribute("user") User user, Model model) {
        model.addAttribute(userService.findByUsername(username));
        model.addAttribute("allRoles",
                roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()));
        return "update_user";
    }


    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        List<Role> roles = new ArrayList<>();
        for (String name : user.getRoleNames()) {
            roles.add(roleService.findRoleByName(name));
        }
        user.setRoles(roles);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles",
                    roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()));
            return "update_user";
        }

        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
