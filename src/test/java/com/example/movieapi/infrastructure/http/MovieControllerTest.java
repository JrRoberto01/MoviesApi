package com.example.movieapi.infrastructure.http;

import com.example.movieapi.application.CreateMovieUseCase;
import com.example.movieapi.application.DeleteMovieUseCase;
import com.example.movieapi.application.GetMovieByIdUseCase;
import com.example.movieapi.application.ListMoviesUseCase;
import com.example.movieapi.application.UpdateMovieUseCase;
import com.example.movieapi.application.output.MovieOutput;
import com.example.movieapi.domain.MovieId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateMovieUseCase createMovieUseCase;

    @MockitoBean
    private ListMoviesUseCase listMoviesUseCase;

    @MockitoBean
    private GetMovieByIdUseCase getMovieByIdUseCase;

    @MockitoBean
    private UpdateMovieUseCase updateMovieUseCase;

    @MockitoBean
    private DeleteMovieUseCase deleteMovieUseCase;

    @Test
    void deve_criar_filme_e_retornar_status_201() throws Exception {
        var id = UUID.randomUUID();

        var output = new MovieOutput(
                id.toString(),
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        when(createMovieUseCase.execute(any()))
                .thenReturn(output);

        var request = """
                {
                  "titulo": "The Matrix",
                  "diretor": "Lana Wachowski",
                  "anoLancamento": 1999
                }
                """;

        mockMvc.perform(
                        post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isCreated())
                .andExpect(
                        header().string(
                                "Location",
                                "/movies/" + id
                        )
                )
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.titulo").value("The Matrix"))
                .andExpect(jsonPath("$.diretor").value("Lana Wachowski"))
                .andExpect(jsonPath("$.anoLancamento").value(1999));

        verify(createMovieUseCase)
                .execute(any());
    }

    @Test
    void deve_listar_filmes_e_retornar_status_200() throws Exception {
        var firstMovie = new MovieOutput(
                UUID.randomUUID().toString(),
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        var secondMovie = new MovieOutput(
                UUID.randomUUID().toString(),
                "Inception",
                "Lana Wachowski",
                2010
        );

        when(listMoviesUseCase.execute())
                .thenReturn(List.of(firstMovie, secondMovie));

        mockMvc.perform(
                        get("/movies")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("The Matrix"))
                .andExpect(jsonPath("$[1].titulo").value("Inception"));

        verify(listMoviesUseCase).execute();
    }

    @Test
    void deve_buscar_filme_por_id_e_retornar_status_200() throws Exception {
        var id = UUID.randomUUID();
        var movieId = new MovieId(id);

        var output = new MovieOutput(
                id.toString(),
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        when(getMovieByIdUseCase.execute(movieId))
                .thenReturn(output);

        mockMvc.perform(
                        get("/movies/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.titulo").value("The Matrix"))
                .andExpect(jsonPath("$.diretor").value("Lana Wachowski"))
                .andExpect(jsonPath("$.anoLancamento").value(1999));

        verify(getMovieByIdUseCase)
                .execute(movieId);
    }

    @Test
    void deve_atualizar_filme_e_retornar_status_200() throws Exception {
        var id = UUID.randomUUID();

        var output = new MovieOutput(
                id.toString(),
                "Inception",
                "Lana Wachowski",
                2010
        );

        when(
                updateMovieUseCase.execute(
                        eq(new MovieId(id)),
                        any()
                )
        ).thenReturn(output);

        var request = """
                {
                  "titulo": "Inception",
                  "diretor": "Lana Wachowski",
                  "anoLancamento": 2010
                }
                """;

        mockMvc.perform(
                        put("/movies/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.titulo").value("Inception"))
                .andExpect(jsonPath("$.diretor").value("Lana Wachowski"))
                .andExpect(jsonPath("$.anoLancamento").value(2010));

        verify(updateMovieUseCase)
                .execute(
                        eq(new MovieId(id)),
                        any()
                );
    }

    @Test
    void deve_excluir_filme_e_retornar_status_204() throws Exception {
        var id = UUID.randomUUID();
        var movieId = new MovieId(id);

        mockMvc.perform(
                        delete("/movies/{id}", id)
                )
                .andExpect(status().isNoContent());

        verify(deleteMovieUseCase)
                .execute(movieId);
    }
}