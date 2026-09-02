package com.example.movieapi.application.input;

public record UpdateMovieInput(
        String titulo,
        String diretor,
        Integer anoLancamento
) {
}
