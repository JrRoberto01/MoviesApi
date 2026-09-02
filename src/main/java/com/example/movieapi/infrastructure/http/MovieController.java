package com.example.movieapi.infrastructure.http;

import com.example.movieapi.application.*;
import com.example.movieapi.domain.MovieId;
import com.example.movieapi.infrastructure.http.request.CreateMovieRequest;
import com.example.movieapi.infrastructure.http.request.UpdateMovieRequest;
import com.example.movieapi.infrastructure.http.response.MovieResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final CreateMovieUseCase createMovieUseCase;
    private final ListMoviesUseCase listMoviesUseCase;
    private final GetMovieByIdUseCase getMovieByIdUseCase;
    private final UpdateMovieUseCase updateMovieUseCase;
    private final DeleteMovieUseCase deleteMovieUseCase;

    @PostMapping
    public ResponseEntity<MovieResponse> create(
            @RequestBody @Valid CreateMovieRequest request
    ) {
        var input = request.toInput();

        var output = createMovieUseCase.execute(input);

        var response = MovieResponse.from(output);

        var location = URI.create("/movies/" + output.id());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public List<MovieResponse> list() {
        return listMoviesUseCase.execute()
                .stream()
                .map(MovieResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public MovieResponse getById(@PathVariable UUID id) {
        var output = getMovieByIdUseCase.execute(
                new MovieId(id)
        );

        return MovieResponse.from(output);
    }

    @PutMapping("/{id}")
    public MovieResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateMovieRequest request
    ) {
        var input = request.toInput();

        var output = updateMovieUseCase.execute(
                new MovieId(id),
                input
        );

        return MovieResponse.from(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteMovieUseCase.execute(
                new MovieId(id)
        );

        return ResponseEntity.noContent().build();
    }
}
