package com.example.movieservice.service;

import com.example.movieservice.model.Genre;
import com.example.movieservice.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Genre not found: " + name));
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Long id, Genre genreDetails) {
        Genre genre = getGenreById(id);

        if (genreDetails.getName() != null) genre.setName(genreDetails.getName());
        if (genreDetails.getDescription() != null) genre.setDescription(genreDetails.getDescription());

        return genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}