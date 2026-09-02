package com.example.movieapi.application;

import com.example.movieapi.application.output.MovieOutput;
import com.example.movieapi.domain.MovieId;
import com.example.movieapi.domain.MovieNotFoundException;
import com.example.movieapi.domain.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetMovieByIdUseCase {

    private final MovieRepository repository;

    public MovieOutput execute(MovieId id) {
        return repository.findById(id)
                .map(MovieOutput::from)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
}
