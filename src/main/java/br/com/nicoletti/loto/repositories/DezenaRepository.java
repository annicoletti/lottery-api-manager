package br.com.nicoletti.loto.repositories;

import br.com.nicoletti.loto.beans.entities.DezenaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DezenaRepository extends JpaRepository<DezenaEntity, Long> {
}
