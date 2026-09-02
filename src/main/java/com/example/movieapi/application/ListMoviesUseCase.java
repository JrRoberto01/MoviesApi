package com.example.movieapi.application;

import com.example.movieapi.application.output.MovieOutput;
import com.example.movieapi.domain.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ListMoviesUseCase {

    private final MovieRepository repository;

    public List<MovieOutput> execute() {
        return repository.findAll()
                .stream()
                .map(MovieOutput::from)
                .toList();
    }
}
