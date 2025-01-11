package com.example.movieservice.controller;

import com.example.movieservice.dto.UserRegistrationDto;
import com.example.movieservice.model.User;
import com.example.movieservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
                               BindingResult result) {
        if (!registrationDto.getPassword().equals(registrationDto.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.user", "Hasła muszą być takie same");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            User user = new User();
            user.setUsername(registrationDto.getUsername());
            user.setEmail(registrationDto.getEmail());
            user.setPassword(registrationDto.getPassword());

            userService.registerUser(user);
            return "redirect:/auth/login?registered=true";
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Email")) {
                result.rejectValue("email", "error.user", "Email jest już zajęty");
            } else if (e.getMessage().contains("Username")) {
                result.rejectValue("username", "error.user", "Nazwa użytkownika jest już zajęta");
            }
            return "auth/register";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/auth/login?logout";
    }
}