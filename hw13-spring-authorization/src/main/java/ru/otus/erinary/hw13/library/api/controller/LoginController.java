package ru.otus.erinary.hw13.library.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-form";
    }

    @GetMapping("/login/failed")
    public String getLoginFailedPage(final Model model) {
        var message = "Invalid username or password";
        model.addAttribute("message", message);
        return "login-form";
    }
}
