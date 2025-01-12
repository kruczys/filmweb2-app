package com.example.movieservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.movieservice.dto.MovieDTO;
import com.example.movieservice.service.MovieService;

@Controller
@RequestMapping("/admin/movies")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMovieController {

    private final MovieService movieService;

    public AdminMovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO movieDTO) {
        try {
            MovieDTO savedMovie = movieService.addMovie(movieDTO);
            return ResponseEntity.ok(savedMovie);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 