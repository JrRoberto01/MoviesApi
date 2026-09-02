package com.example.movieapi.infrastructure.persistence.repository;

import com.example.movieapi.domain.Movie;
import com.example.movieapi.domain.MovieId;
import com.example.movieapi.domain.MovieRepository;
import com.example.movieapi.infrastructure.persistence.entity.MovieEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Repository
public class JpaMovieRepository  implements MovieRepository {

    private final MovieCrudRepository repository;

    @Override
    public Movie save(Movie movie) {
        var entity = toEntity(movie);
        var saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public List<Movie> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Movie> findById(MovieId id) {
        return repository.findById((id.id()))
                .map(this::toDomain);
    }

    @Override
    public void delete(MovieId id) {
        repository.deleteById(id.id());
    }

    private MovieEntity toEntity(Movie movie) {
        return new MovieEntity(
                movie.getId().id(),
                movie.getTitulo(),
                movie.getDiretor(),
                movie.getAnoLancamento()
        );
    }

    private Movie toDomain(MovieEntity entity) {
        return new Movie(
                new MovieId(entity.getId()),
                entity.getTitulo(),
                entity.getDiretor(),
                entity.getAnoLancamento()
        );
    }
}
