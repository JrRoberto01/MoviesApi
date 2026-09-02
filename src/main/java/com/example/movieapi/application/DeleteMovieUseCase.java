package com.example.movieapi.application;

import com.example.movieapi.domain.MovieId;
import com.example.movieapi.domain.MovieNotFoundException;
import com.example.movieapi.domain.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteMovieUseCase {

    private final MovieRepository repository;

    public void execute(MovieId movieId) {
        if (repository.findById(movieId).isEmpty()) {
            throw new MovieNotFoundException(movieId);
        }

        repository.delete(movieId);
    }
}
