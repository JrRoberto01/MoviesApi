package com.example.movieapi.application;

import com.example.movieapi.application.input.CreateMovieInput;
import com.example.movieapi.application.output.MovieOutput;
import com.example.movieapi.domain.Movie;
import com.example.movieapi.domain.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CreateMovieUseCase {

    private final MovieRepository repository;

    public MovieOutput execute(CreateMovieInput input) {
        var movie = new Movie(
                input.titulo(),
                input.diretor(),
                input.anoLancamento()
        );

        var saved = repository.save(movie);

        return MovieOutput.from(saved);
    }
}
