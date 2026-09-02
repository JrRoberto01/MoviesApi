package com.example.movieapi.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.time.Year;

@Getter
public class Movie {

    private final MovieId id;
    private String titulo;
    private String diretor;
    private Integer anoLancamento;

    public Movie(String titulo, String diretor, Integer anoLancamento) {
        validate(titulo, diretor, anoLancamento);

        this.id = new MovieId();
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
    }

    public Movie(MovieId id, String titulo, String diretor, Integer anoLancamento) {
        Assert.notNull(id, "O identificador do filme nao pode ser nulo");

        validate(titulo, diretor, anoLancamento);

        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
    }

    public void update(String titulo, String diretor, Integer anoLancamento) {
        validate(titulo, diretor, anoLancamento);

        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
    }

    private static void validate(String titulo, String diretor, Integer anoLancamento) {
        Assert.hasText(titulo, "O titulo nao pode estar vazio");
        Assert.isTrue(titulo.length() >= 3 && titulo.length() <= 150, "O titulo deve possuir entre 3 e 150 caracteres");

        Assert.hasText(diretor, "O diretor nao pode estar vazio");
        Assert.isTrue(diretor.length() >= 3 && diretor.length() <= 100, "O diretor deve possuir entre 3 e 100 caracteres");

        Assert.notNull(anoLancamento, "O ano de lancamento nao pode ser nulo");
        Assert.isTrue(anoLancamento > 0, "O ano de lancamento deve ser positivo");
        Assert.isTrue(anoLancamento <= Year.now().getValue(), "O ano de lancamento nao pode ser maior que o ano atual");
    }
}