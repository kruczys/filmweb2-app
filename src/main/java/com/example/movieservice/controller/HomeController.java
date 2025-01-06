package com.example.movieservice.controller;

import com.example.movieservice.service.MovieService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {
        PageRequest latestMoviesPageable = PageRequest.of(0, 10,
                Sort.by(Sort.Direction.DESC, "releaseDate"));

        model.addAttribute("latestMovies", movieService.getAllMovies(latestMoviesPageable).getContent());
        model.addAttribute("popularMovies", movieService.getMostPopularMovies());

        return "index";
    }
}