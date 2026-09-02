package com.example.movieapi.infrastructure.persistence.repository;

import com.example.movieapi.infrastructure.persistence.entity.MovieEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MovieCrudRepository extends CrudRepository<MovieEntity, UUID> {
}
