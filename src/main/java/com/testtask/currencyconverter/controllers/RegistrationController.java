package com.testtask.currencyconverter.controllers;

import com.testtask.currencyconverter.entities.auth.User;
import com.testtask.currencyconverter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(User user, Model model) {
        boolean success = userService.addUser(model, user);

        return success ? "redirect:/" : "registration";
    }

    @GetMapping
    public String getRegistrationPage() {
        return "registration";
    }
}
