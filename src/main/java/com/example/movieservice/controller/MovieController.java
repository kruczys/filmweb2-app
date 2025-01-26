package com.example.movieservice.controller;

import com.example.movieservice.dto.MovieDTO;
import com.example.movieservice.model.Movie;
import com.example.movieservice.model.User;
import com.example.movieservice.service.MovieService;
import com.example.movieservice.service.UserService;
import com.example.movieservice.repository.MovieRepository;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final MovieRepository movieRepository;

    public MovieController(MovieService movieService, UserService userService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.userService = userService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public String listMovies(Model model, 
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "12") int size,
                            @RequestParam(defaultValue = "releaseDate") String sort,
                            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Page<Movie> movies;
        
        if (sort.equals("averageRating")) {
            movies = sortDirection == Sort.Direction.DESC ? 
                movieService.findAllByOrderByAverageRatingDesc(PageRequest.of(page, size)) :
                movieService.findAllByOrderByAverageRatingAsc(PageRequest.of(page, size));
        } else {
            movies = movieRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortDirection, sort))
            );
        }
        
        model.addAttribute("movies", movies);
        model.addAttribute("sortBy", sort);
        model.addAttribute("direction", direction);
        
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