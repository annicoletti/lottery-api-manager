package br.com.nicoletti.loto.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.loto.beans.entities.JogoEntity;

@Repository
public interface JogoRepository extends JpaRepository<JogoEntity, Long> {

    Optional<JogoEntity> findByNumeroConcursoAndTipoJogoId(Integer numeroAposta, Long tipoJogoId);

    Optional<JogoEntity> findFirstByTipoJogoIdOrderByNumeroConcursoDesc(Long tipoJogoId);

}
