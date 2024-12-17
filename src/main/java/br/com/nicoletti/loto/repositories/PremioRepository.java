package br.com.nicoletti.loto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.loto.beans.entities.PremioEntity;

@Repository
public interface PremioRepository extends JpaRepository<PremioEntity, Long> {
}
