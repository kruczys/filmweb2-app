package com.example.movieservice.service;

import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CastMemberRepository castMemberRepository;
    private final ReviewRepository reviewRepository;

    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        CastMemberRepository castMemberRepository,
                        ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.castMemberRepository = castMemberRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Movie> getMostPopularMovies() {
        return movieRepository.findMostPopularMovies();
    }

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public Page<Movie> searchMovies(String title, String genre, String castMember, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return movieRepository.findAll(pageable);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (movieDetails.getTitle() != null) movie.setTitle(movieDetails.getTitle());
        if (movieDetails.getDescription() != null) movie.setDescription(movieDetails.getDescription());
        if (movieDetails.getReleaseDate() != null) movie.setReleaseDate(movieDetails.getReleaseDate());
        if (movieDetails.getImageUrl() != null) movie.setImageUrl(movieDetails.getImageUrl());
        if (movieDetails.getTrailerUrl() != null) movie.setTrailerUrl(movieDetails.getTrailerUrl());
        if (movieDetails.getCast() != null) movie.setCast(movieDetails.getCast());
        if (movieDetails.getGenres() != null) movie.setGenres(movieDetails.getGenres());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Double getAverageRating(Long movieId) {
        return reviewRepository.calculateAverageRatingForMovie(movieId);
    }
}