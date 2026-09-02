package com.example.movieapi.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record MovieId(UUID id) {
    public MovieId {
        Assert.notNull(id, "O identificador do filme nao pode ser nulo");
    }

    public MovieId() {
        this(UUID.randomUUID());
    }
}
