package com.example.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.movieservice.model.Genre;
import com.example.movieservice.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/api/admin/genres")
public class AdminGenreController {
    
    @Autowired
    private GenreService genreService;
    
    @PostMapping
    @ResponseBody
    public Genre addGenre(@RequestBody Genre genre) {
        return genreService.addGenre(genre);
    }
    
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }
    
    @GetMapping
    @ResponseBody
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
} 