# Movie API

API RESTful para gerenciamento de filmes, desenvolvida com Java e Spring Boot.

## Sobre o projeto

Este projeto foi desenvolvido como material prático para uma aula introdutória de desenvolvimento de APIs RESTful com Java e Spring Boot.

A aplicação foi preparada no contexto de uma atividade de monitoria acadêmica, a partir de uma solicitação do professor responsável pela disciplina, com o objetivo de utilizar um projeto completo como base para explicar, de forma progressiva, os principais conceitos envolvidos na construção de uma API REST.

Durante a aula, o projeto pode ser utilizado para demonstrar na prática conceitos como:

- estruturação de uma aplicação Spring Boot;
- criação de endpoints REST;
- separação entre domínio, aplicação e infraestrutura;
- uso de DTOs de entrada e saída;
- criação de casos de uso;
- inversão de dependência por meio de interfaces;
- persistência com Spring Data JPA;
- utilização de H2 e PostgreSQL;
- execução de PostgreSQL com Docker Compose;
- validação com Jakarta Bean Validation;
- tratamento global de exceções;
- uso correto dos principais status HTTP;
- testes unitários com JUnit 5 e Mockito;
- testes da camada HTTP com MockMvc;
- testes de integração da persistência.

O objetivo principal não é apenas apresentar uma API funcionando, mas utilizar cada parte da implementação como apoio para explicar **por que ela existe, qual responsabilidade possui e como as camadas se relacionam durante uma requisição HTTP**.

O fluxo completo de criação de um filme, por exemplo, permite acompanhar:

```text
JSON
 â†“
CreateMovieRequest
 â†“
CreateMovieInput
 â†“
CreateMovieUseCase
 â†“
Movie
 â†“
MovieRepository
 â†“
JpaMovieRepository
 â†“
MovieEntity
 â†“
MovieCrudRepository
 â†“
Hibernate
 â†“
Banco de dados