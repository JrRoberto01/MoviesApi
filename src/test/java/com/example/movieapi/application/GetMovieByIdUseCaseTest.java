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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMovieByIdUseCaseTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private GetMovieByIdUseCase useCase;

    @Test
    void deve_retornar_filme_quando_encontrado() {
        var movieId = new MovieId(UUID.randomUUID());

        var movie = new Movie(
                movieId,
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        when(repository.findById(movieId))
                .thenReturn(Optional.of(movie));

        var output = useCase.execute(movieId);

        assertNotNull(output);
        assertEquals(movieId.id().toString(), output.id());
        assertEquals("The Matrix", output.titulo());
        assertEquals("Lana Wachowski", output.diretor());
        assertEquals(1999, output.anoLancamento());

        verify(repository, times(1))
                .findById(movieId);
    }

    @Test
    void deve_lancar_excecao_quando_filme_nao_for_encontrado() {
        var movieId = new MovieId(UUID.randomUUID());

        when(repository.findById(movieId))
                .thenReturn(Optional.empty());

        var exception = assertThrows(
                MovieNotFoundException.class,
                () -> useCase.execute(movieId)
        );

        assertEquals(
                "Filme com identificador " + movieId.id() + " nao encontrado",
                exception.getMessage()
        );

        verify(repository, times(1))
                .findById(movieId);
    }
}