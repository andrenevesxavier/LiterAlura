package com.desafio.LiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecordLivro(@JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<RecordAutor> autor,
                          @JsonAlias("languages") List<String> idioma,
                          @JsonAlias("download_count") Integer numeroDownloads) {
}
