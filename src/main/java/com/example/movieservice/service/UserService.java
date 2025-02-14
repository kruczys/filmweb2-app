package com.example.movieservice.service;

import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.model.enums.Role;
import com.example.movieservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie znaleziony: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email już istnieje");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Nazwa użytkownika już istnieje");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setRegistrationDate(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setFavoriteMovies(new HashSet<>());
        user.setWatchList(new HashSet<>());
        user.setIgnoredMovies(new HashSet<>());

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

    public Set<Movie> getFavoriteMovies(Long userId) {
        User user = getUserById(userId);
        return user.getFavoriteMovies();
    }

    public Set<Movie> getWatchlist(Long userId) {
        User user = getUserById(userId);
        return user.getWatchList();
    }

    public List<User> getMostActiveUsers() {
        return userRepository.findMostActiveUsers();
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        if (userDetails.getUsername() != null) {
            if (!user.getUsername().equals(userDetails.getUsername()) &&
                    userRepository.existsByUsername(userDetails.getUsername())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(userDetails.getUsername());
        }

        if (userDetails.getEmail() != null) {
            if (!user.getEmail().equals(userDetails.getEmail()) &&
                    userRepository.existsByEmail(userDetails.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(userDetails.getEmail());
        }

        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
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

    public Set<Movie> getIgnoredMovies(Long userId) {
        User user = getUserById(userId);
        return user.getIgnoredMovies();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie znaleziony: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}