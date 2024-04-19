package com.imdb.imdb.repository;

import com.imdb.imdb.model.Filme.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    @Query(value = "select f from Filme f where f.nome ilike %?1% or f.atores ilike %?1% or f.diretor ilike %?1% or f.genero ilike %?1%")
    List<Filme> findBySearch(String search);

}
