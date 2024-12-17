package br.com.nicoletti.loto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.loto.beans.entities.DezenaSorteadaEntity;

@Repository
public interface DezenaSorteadaRepository extends JpaRepository<DezenaSorteadaEntity, Long> {
}
