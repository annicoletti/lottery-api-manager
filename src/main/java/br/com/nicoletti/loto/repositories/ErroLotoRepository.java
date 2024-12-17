package br.com.nicoletti.loto.repositories;

import br.com.nicoletti.loto.beans.entities.ErroLotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErroLotoRepository extends JpaRepository<ErroLotoEntity, Long> {
    ErroLotoEntity findByNumeroConcursoAndTipoJogo(Integer numeroConcurso, String tipoJogo);
}
