package com.desafio.LiterAlura.repository;

import com.desafio.LiterAlura.Model.Autor;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {

    Optional<Autor> findByNomeAutor (String nome);

    @Query("select a from Autor a where a.dataNascimento <=:anoEscolhido AND a.dataFalecimento >=:anoEscolhido")
    List<Autor> buscarAutorPeloAno(Integer anoEscolhido);
}
