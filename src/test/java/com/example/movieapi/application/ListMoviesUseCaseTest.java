package com.example.movieapi.application;

import com.example.movieapi.domain.Movie;
import com.example.movieapi.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListMoviesUseCaseTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private ListMoviesUseCase useCase;

    @Test
    void deve_listar_filmes_com_sucesso() {
        var firstMovie = new Movie(
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        var secondMovie = new Movie(
                "Inception",
                "Lana Wachowski",
                2010
        );

        when(repository.findAll())
                .thenReturn(List.of(firstMovie, secondMovie));

        var output = useCase.execute();

        assertEquals(2, output.size());

        assertEquals("The Matrix", output.get(0).titulo());
        assertEquals("Lana Wachowski", output.get(0).diretor());
        assertEquals(1999, output.get(0).anoLancamento());

        assertEquals("Inception", output.get(1).titulo());
        assertEquals("Lana Wachowski", output.get(1).diretor());
        assertEquals(2010, output.get(1).anoLancamento());

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void deve_retornar_lista_vazia_quando_nao_houver_filmes() {
        when(repository.findAll())
                .thenReturn(List.of());

        var output = useCase.execute();

        assertTrue(output.isEmpty());

        verify(repository, times(1))
                .findAll();
    }
}