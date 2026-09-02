package com.example.movieapi.infrastructure.persistence;

import com.example.movieapi.domain.Movie;
import com.example.movieapi.domain.MovieId;
import com.example.movieapi.infrastructure.persistence.repository.MovieCrudRepository;
import com.example.movieapi.infrastructure.persistence.repository.JpaMovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("h2")
@Import(JpaMovieRepository.class)
class JpaMovieRepositoryTest {

    @Autowired
    private JpaMovieRepository repository;

    @Autowired
    private MovieCrudRepository crudRepository;

    @BeforeEach
    void setUp() {
        crudRepository.deleteAll();
    }

    @Test
    void deve_salvar_e_buscar_filme_por_id() {
        var movie = new Movie(
                "The Matrix",
                "Lana Wachowski",
                1999
        );

        var saved = repository.save(movie);

        var result = repository.findById(
                saved.getId()
        );

        assertThat(result).isPresent();

        assertThat(result.get().getId())
                .isEqualTo(movie.getId());

        assertThat(result.get().getTitulo())
                .isEqualTo("The Matrix");

        assertThat(result.get().getDiretor())
                .isEqualTo("Lana Wachowski");

        assertThat(result.get().getAnoLancamento())
                .isEqualTo(1999);
    }

    @Test
    void deve_listar_filmes_persistidos() {
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

        repository.save(firstMovie);
        repository.save(secondMovie);

        var movies = repository.findAll();

        assertThat(movies)
                .hasSize(2);

        assertThat(movies)
                .extracting(Movie::getId)
                .containsExactlyInAnyOrder(
                        firstMovie.getId(),
                        secondMovie.getId()
                );
    }

    @Test
    void deve_retornar_vazio_ao_buscar_filme_inexistente() {
        var nonExistentId = new MovieId();

        var result = repository.findById(
                nonExistentId
        );

        assertThat(result).isEmpty();
    }

    @Test
    void deve_atualizar_filme_persistido() {
        var movie = repository.save(
                new Movie(
                        "The Matrix",
                        "Lana Wachowski",
                        1999
                )
        );

        movie.update(
                "Inception",
                "Lana Wachowski",
                2010
        );

        repository.save(movie);

        var result = repository.findById(
                movie.getId()
        );

        assertThat(result).isPresent();

        assertThat(result.get().getTitulo())
                .isEqualTo("Inception");

        assertThat(result.get().getDiretor())
                .isEqualTo("Lana Wachowski");

        assertThat(result.get().getAnoLancamento())
                .isEqualTo(2010);
    }

    @Test
    void deve_excluir_filme_por_id() {
        var movie = repository.save(
                new Movie(
                        "The Matrix",
                        "Lana Wachowski",
                        1999
                )
        );

        var movieId = movie.getId();

        repository.delete(movieId);

        var result = repository.findById(movieId);

        assertThat(result).isEmpty();
    }
}
