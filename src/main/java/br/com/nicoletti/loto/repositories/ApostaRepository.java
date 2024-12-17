package br.com.nicoletti.loto.repositories;

import br.com.nicoletti.loto.beans.entities.ApostaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApostaRepository extends JpaRepository<ApostaEntity, Long> {
}
