package br.com.nicoletti.loto.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.loto.beans.entities.TipoJogoEntity;

@Repository
public interface TipoJogoRepository extends JpaRepository<TipoJogoEntity, Long> {

    Optional<TipoJogoEntity> findByNome(String tipoJogo);
}
