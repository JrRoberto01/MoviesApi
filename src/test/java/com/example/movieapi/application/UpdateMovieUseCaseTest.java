package com.example.movieapi.application;

import com.example.movieapi.application.input.UpdateMovieInput;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMovieUseCaseTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private UpdateMovieUseCase useCase;

    @Test
    void deve_atualizar_filme_com_sucesso() {
        var movieId = new MovieId(UUID.randomUUID());

        var movie = new Movie(
                movieId,
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        var input = new UpdateMovieInput(
                "Inception",
                "Lana Wachowski",
                2010
        );

        when(repository.findById(movieId))
                .thenReturn(Optional.of(movie));

        when(repository.save(any(Movie.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var output = useCase.execute(movieId, input);

        assertEquals(movieId.id().toString(), output.id());
        assertEquals("Inception", output.titulo());
        assertEquals("Lana Wachowski", output.diretor());
        assertEquals(2010, output.anoLancamento());

        verify(repository, times(1))
                .findById(movieId);

        verify(repository, times(1))
                .save(movie);
    }

    @Test
    void deve_lancar_excecao_ao_atualizar_filme_inexistente() {
        var movieId = new MovieId(UUID.randomUUID());

        var input = new UpdateMovieInput(
                "Inception",
                "Lana Wachowski",
                2010
        );

        when(repository.findById(movieId))
                .thenReturn(Optional.empty());

        assertThrows(
                MovieNotFoundException.class,
                () -> useCase.execute(movieId, input)
        );

        verify(repository, times(1))
                .findById(movieId);

        verify(repository, never())
                .save(any(Movie.class));
    }
}