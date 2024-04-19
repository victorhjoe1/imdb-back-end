package com.imdb.imdb.repository;

import com.imdb.imdb.model.Filme.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {
    Optional<Voto> findByIdUsuarioAndIdFilme(Integer idUsuario, Integer idFilme);
}
