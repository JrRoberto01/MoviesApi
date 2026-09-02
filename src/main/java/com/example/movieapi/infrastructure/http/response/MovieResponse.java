package com.example.movieapi.infrastructure.http.response;

import com.example.movieapi.application.output.MovieOutput;

public record MovieResponse(
        String id,
        String titulo,
        String diretor,
        Integer anoLancamento
) {

    public static MovieResponse from(MovieOutput output) {
        return new MovieResponse(
                output.id(),
                output.titulo(),
                output.diretor(),
                output.anoLancamento()
        );
    }
}