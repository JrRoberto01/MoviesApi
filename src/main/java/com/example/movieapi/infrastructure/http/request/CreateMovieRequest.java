package com.example.movieapi.infrastructure.http.request;

import com.example.movieapi.application.input.CreateMovieInput;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record CreateMovieRequest(

        @NotBlank(message = "O titulo e obrigatorio")
        @Size(
                min = 3,
                max = 150,
                message = "O titulo deve possuir entre 3 e 150 caracteres"
        )
        String titulo,

        @NotBlank(message = "O diretor e obrigatorio")
        @Size(
                min = 3,
                max = 100,
                message = "O diretor deve possuir entre 3 e 100 caracteres"
        )
        String diretor,

        @NotNull(message = "O ano de lancamento e obrigatorio")
        @Positive(message = "O ano de lancamento deve ser positivo")
        Integer anoLancamento

) {

    @AssertTrue(message = "O ano de lancamento nao pode ser maior que o ano atual")
    public boolean isAnoLancamentoValid() {
        return anoLancamento == null
                || anoLancamento <= Year.now().getValue();
    }

    public CreateMovieInput toInput() {
        return new CreateMovieInput(
                titulo,
                diretor,
                anoLancamento
        );
    }
}
