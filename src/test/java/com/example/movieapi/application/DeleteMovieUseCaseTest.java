package com.example.movieapi.application;

import com.example.movieapi.domain.Movie;
import com.example.movieapi.domain.MovieId;
import com.example.movieapi.domain.MovieNotFoundException;
import com.example.movieapi.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteMovieUseCaseTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private DeleteMovieUseCase useCase;

    @Test
    void deve_excluir_filme_com_sucesso() {
        var movieId = new MovieId(UUID.randomUUID());

        var movie = new Movie(
                movieId,
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        when(repository.findById(movieId))
                .thenReturn(Optional.of(movie));

        useCase.execute(movieId);

        verify(repository, times(1))
                .findById(movieId);

        verify(repository, times(1))
                .delete(movieId);
    }

    @Test
    void deve_lancar_excecao_ao_excluir_filme_inexistente() {
        var movieId = new MovieId(UUID.randomUUID());

        when(repository.findById(movieId))
                .thenReturn(Optional.empty());

        assertThrows(
                MovieNotFoundException.class,
                () -> useCase.execute(movieId)
        );

        verify(repository, times(1))
                .findById(movieId);

        verify(repository, never())
                .delete(movieId);
    }
}