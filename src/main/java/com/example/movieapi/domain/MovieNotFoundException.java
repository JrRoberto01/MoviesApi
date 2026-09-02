package com.example.movieapi.domain;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(MovieId movieId) {
        super("Filme com identificador " + movieId.id() + " nao encontrado");
    }
}
