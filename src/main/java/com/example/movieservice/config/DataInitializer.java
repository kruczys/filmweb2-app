package com.example.movieservice.config;

import com.example.movieservice.model.User;
import com.example.movieservice.model.enums.Role;
import com.example.movieservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@example.com");
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            admin.setRegistrationDate(LocalDateTime.now());
            userRepository.save(admin);
        }
    }
} 