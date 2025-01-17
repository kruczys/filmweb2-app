package com.example.movieservice.controller;

import com.example.movieservice.dto.MovieDTO;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.service.MovieService;
import com.example.movieservice.service.UserService;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;

    public MovieController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping
    public String showMoviesList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            Model model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Movie> movies = movieService.getAllMovies(pageRequest);

        model.addAttribute("movies", movies);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model, Principal principal) {
        Movie movie = movieService.getMovieEntityById(id);
        model.addAttribute("movie", movie);
        
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            user.getWatchList().size();
            user.getFavoriteMovies().size();
            model.addAttribute("user", user);
            model.addAttribute("isInWatchlist", user.getWatchList().contains(movie));
            model.addAttribute("isInFavorites", user.getFavoriteMovies().contains(movie));
        }
        
        return "movies/details";
    }

    @GetMapping("/search")
    public String searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String castMember,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Movie> movies = movieService.searchMovies(title, genre, castMember, pageRequest);

        model.addAttribute("movies", movies);
        model.addAttribute("title", title);
        model.addAttribute("genre", genre);
        model.addAttribute("castMember", castMember);
        model.addAttribute("param.title", title);
        return "movies/search-results";
    }

    @GetMapping("/api/popular")
    @ResponseBody
    public ResponseEntity<?> getPopularMovies() {
        return ResponseEntity.ok(movieService.getMostPopularMovies());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}