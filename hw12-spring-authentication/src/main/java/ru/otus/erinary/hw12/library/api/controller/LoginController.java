package ru.otus.erinary.hw12.library.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-form";
    }
}
