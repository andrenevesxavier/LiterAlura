package com.desafio.LiterAlura.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Getter
@Setter
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nomeAutor;
    private Integer dataNascimento;
    private Integer dataFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DadosLivro> dadosLivros = new ArrayList<>();

    public Autor(RecordAutor recordAutor) {
        this.nomeAutor = recordAutor.nome();
        this.dataNascimento = recordAutor.dataNasc();
        this.dataFalecimento = recordAutor.anoFalec();
    }

    public Autor() {
    }

    @Override
    public String toString() {
        return "-----Autor------" + "\n" +
                "Nome do autor: " + nomeAutor + "\n"+
                "Data de nascimento: " + dataNascimento + "\n"+
                "Data de falecimento: " + dataFalecimento + "\n" +
                "Livros: " + dadosLivros + "\n";
    }
}
