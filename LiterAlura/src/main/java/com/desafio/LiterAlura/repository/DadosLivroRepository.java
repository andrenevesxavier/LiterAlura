package com.desafio.LiterAlura.repository;

import com.desafio.LiterAlura.Model.DadosLivro;
import com.desafio.LiterAlura.Model.RecordLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DadosLivroRepository extends JpaRepository<DadosLivro, Long> {

    Optional<DadosLivro> findByTitulo(String titulo);

    @Query("SELECT d.titulo FROM DadosLivro d JOIN d.autor a WHERE a.id = :autorId")
    List <String> findTitulosByAutorId(Long autorId);

    @Query("select d from DadosLivro d where UPPER (d.idioma) = UPPER(:idiomaEscolhido)")
    List<DadosLivro> buscarLivroIdioma(String idiomaEscolhido);
}
