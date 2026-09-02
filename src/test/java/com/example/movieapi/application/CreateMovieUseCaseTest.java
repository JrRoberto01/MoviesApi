package com.example.movieapi.application;

import com.example.movieapi.application.input.CreateMovieInput;
import com.example.movieapi.domain.Movie;
import com.example.movieapi.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMovieUseCaseTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private CreateMovieUseCase useCase;

    @Test
    void deve_criar_filme_com_sucesso() {
        var input = new CreateMovieInput(
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        when(repository.save(any(Movie.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var output = useCase.execute(input);

        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals("The Matrix", output.titulo());
        assertEquals("Lana Wachowski", output.diretor());
        assertEquals(1999, output.anoLancamento());

        verify(repository, times(1))
                .save(any(Movie.class));
    }
}