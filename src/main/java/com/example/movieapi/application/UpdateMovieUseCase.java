package com.example.movieapi.application;

import com.example.movieapi.application.input.UpdateMovieInput;
import com.example.movieapi.application.output.MovieOutput;
import com.example.movieapi.domain.MovieId;
import com.example.movieapi.domain.MovieNotFoundException;
import com.example.movieapi.domain.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdateMovieUseCase {

    private final MovieRepository repository;

    public MovieOutput execute(MovieId id, UpdateMovieInput input) {
        var movie = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        movie.update(
                input.titulo(),
                input.diretor(),
                input.anoLancamento()
        );

        var updated = repository.save(movie);

        return MovieOutput.from(updated);
    }
}
