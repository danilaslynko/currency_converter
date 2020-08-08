package com.testtask.currencyconverter.services;

import com.testtask.currencyconverter.entities.auth.Role;
import com.testtask.currencyconverter.entities.auth.User;
import com.testtask.currencyconverter.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    final UserRepo userRepo;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    // Method validates new User object before storing it into DB and stores it.
    public boolean addUser(Model model, User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());

        // regex for checking password: 8-20 symbols, at least 1 number, at least 1 latin character
        String passwordRegex = "((?=.*\\d)(?=.*[a-zA-Z]).{8,20})";

        if (userFromDB != null) {
            model.addAttribute(
                    "usernameExistsError",
                    "Пользователь с таким именем уже существует"
            );
            return false;
        }
        if (!user.getPassword().matches(passwordRegex)) {
            model.addAttribute(
                    "passwordDoesntMatchRegexError",
                    "Пароль должен содержать цифры и буквы латинского алфавита и состоять из 8-20 символов"
            );
            return false;
        }
        if (!user.getPassword().equals(user.getPasswordValidation())) {
            model.addAttribute(
                    "passwordsDoesntEqualsError",
                    "Пароли не совпадают"
            );
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return true;
    }
}
