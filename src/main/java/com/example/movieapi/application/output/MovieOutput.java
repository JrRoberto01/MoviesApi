package com.example.movieapi.application.output;

import com.example.movieapi.domain.Movie;

public record MovieOutput(String id, String titulo, String diretor, Integer anoLancamento) {

    public static MovieOutput from(Movie movie) {
        return new MovieOutput(
                movie.getId().id().toString(),
                movie.getTitulo(),
                movie.getDiretor(),
                movie.getAnoLancamento()
        );
    }
}
