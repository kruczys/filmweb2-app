package com.example.movieservice.controller;

import com.example.movieservice.model.User;
import com.example.movieservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (error != null) {
            model.addAttribute("error", "Nieprawidłowa nazwa użytkownika lub hasło");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(User user, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerUser(user);
            return "redirect:/auth/login?registered=true";
        } catch (Exception e) {
            result.rejectValue("email", "error.user",
                    "Użytkownik z takim emailem już istnieje");
            return "auth/register";
        }
    }
}