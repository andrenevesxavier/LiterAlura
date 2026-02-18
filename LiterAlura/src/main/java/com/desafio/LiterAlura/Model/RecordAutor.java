package com.desafio.LiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecordAutor(@JsonAlias("name") String nome,
                          @JsonAlias("birth_year") Integer dataNasc,
                          @JsonAlias("death_year") Integer anoFalec) {
}
