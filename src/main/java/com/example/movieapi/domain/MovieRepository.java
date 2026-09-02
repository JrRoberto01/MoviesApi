package com.example.movieapi.domain;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    Movie save(Movie movie);

    List<Movie> findAll();

    Optional<Movie> findById(MovieId id);

    void delete(MovieId id);
}
