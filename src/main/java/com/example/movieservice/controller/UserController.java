package com.example.movieservice.controller;

import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.service.MovieService;
import com.example.movieservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.security.Principal;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final MovieService movieService;

    public UserController(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @PostMapping("/{userId}/favorites/{movieId}")
    public ResponseEntity<?> addToFavorites(@PathVariable Long userId, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieEntityById(movieId);
        userService.addToFavorites(userId, movie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/favorites/{movieId}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable Long userId, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieEntityById(movieId);
        userService.removeFromFavorites(userId, movie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/watchlist/{movieId}")
    public ResponseEntity<?> addToWatchlist(@PathVariable Long userId, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieEntityById(movieId);
        userService.addToWatchlist(userId, movie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/watchlist/{movieId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable Long userId, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieEntityById(movieId);
        userService.removeFromWatchlist(userId, movie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/ignored/{movieId}")
    public ResponseEntity<?> addToIgnored(@PathVariable Long userId, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieEntityById(movieId);
        userService.addToIgnored(userId, movie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/ignored/{movieId}")
    public ResponseEntity<?> removeFromIgnored(@PathVariable Long userId, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieEntityById(movieId);
        userService.removeFromIgnored(userId, movie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/watchlist")
    public String showWatchlist(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("movies", user.getWatchList());
        return "user/watchlist";
    }

    @GetMapping("/favorites")
    public String showFavorites(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("movies", user.getFavoriteMovies());
        return "user/favorites";
    }
}