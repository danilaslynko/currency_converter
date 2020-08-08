package com.testtask.currencyconverter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) Object error) {
        if (error != null) {
            model.addAttribute("error", "Неверные логин или пароль");
        }

        return "login";
    }
}
