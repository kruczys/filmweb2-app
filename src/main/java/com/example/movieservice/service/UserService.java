package com.example.movieservice.service;

import com.example.movieservice.model.User;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.enums.Role;
import com.example.movieservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void addToFavorites(Long userId, Movie movie) {
        User user = getUserById(userId);
        user.getFavoriteMovies().add(movie);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromFavorites(Long userId, Movie movie) {
        User user = getUserById(userId);
        user.getFavoriteMovies().remove(movie);
        userRepository.save(user);
    }

    @Transactional
    public void addToWatchlist(Long userId, Movie movie) {
        User user = getUserById(userId);
        user.getWatchList().add(movie);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromWatchlist(Long userId, Movie movie) {
        User user = getUserById(userId);
        user.getWatchList().remove(movie);
        userRepository.save(user);
    }

    public List<User> getMostActiveUsers() {
        return userRepository.findMostActiveUsers();
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        if (userDetails.getUsername() != null) user.setUsername(userDetails.getUsername());
        if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void addToIgnored(Long userId, Movie movie) {
        User user = getUserById(userId);
        user.getIgnoredMovies().add(movie);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromIgnored(Long userId, Movie movie) {
        User user = getUserById(userId);
        user.getIgnoredMovies().remove(movie);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}