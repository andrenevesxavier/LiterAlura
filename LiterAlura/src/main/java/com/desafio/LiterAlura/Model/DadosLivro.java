package com.desafio.LiterAlura.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dados_livros")
@Getter
@Setter
public class DadosLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private int numeroDownloads;

    @ManyToOne
    private Autor autor;

    public DadosLivro( RecordLivro recordLivro) {
        this.titulo = recordLivro.titulo();
        this.idioma = recordLivro.idioma().get(0);
        this.numeroDownloads = recordLivro.numeroDownloads();
    }

    public DadosLivro() {
    }

    @Override
    public String toString() {
        return "--------LIVRO---------- " + "\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNomeAutor() + "\n"+
                "Idioma: " + idioma + "\n"+
                "Numero de downloads: " + numeroDownloads + "\n";
    }
}
