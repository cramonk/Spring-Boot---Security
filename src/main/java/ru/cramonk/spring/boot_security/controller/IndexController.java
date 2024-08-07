package ru.cramonk.spring.boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String getIndex() {
        return "index";
    }
}
