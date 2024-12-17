package br.com.nicoletti.loto.services;

import br.com.nicoletti.loto.beans.dto.TipoJogoDTO;
import br.com.nicoletti.loto.beans.entities.TipoJogoEntity;
import br.com.nicoletti.loto.exceptions.DatabaseException;
import br.com.nicoletti.loto.exceptions.ResourceNotFoundException;
import br.com.nicoletti.loto.repositories.TipoJogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TipoJogoService {

    @Autowired
    private TipoJogoRepository tipoJogoRepository;

    @Transactional
    public Page<TipoJogoDTO> list(PageRequest pageRequest) {
        Page<TipoJogoEntity> pageable = tipoJogoRepository.findAll(pageRequest);
        return pageable.map(TipoJogoDTO::new);
    }

    @Transactional
    public TipoJogoDTO get(Long id) {
        Optional<TipoJogoEntity> optional = tipoJogoRepository.findById(id);
        TipoJogoEntity entity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new TipoJogoDTO(entity);
    }

    @Transactional
    public TipoJogoDTO create(TipoJogoDTO dto) {
        TipoJogoEntity entity = new TipoJogoEntity();
        parseDtoToEntity(dto, entity);
        tipoJogoRepository.save(entity);
        return new TipoJogoDTO(entity);
    }

    @Transactional
    public TipoJogoDTO update(Long id, TipoJogoDTO dto) {
        try {
            TipoJogoEntity entity = tipoJogoRepository.getOne(id);
            parseDtoToEntity(dto, entity);
            tipoJogoRepository.save(entity);
            return new TipoJogoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            TipoJogoDTO dto = this.get(id);
            tipoJogoRepository.deleteById(dto.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void parseDtoToEntity(TipoJogoDTO dto, TipoJogoEntity entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setQuantidadeNumerosDisponiveis(dto.getQuantidadeNumerosDisponiveis());
        entity.setQuantidadeNumerosMaximo(dto.getQuantidadeNumerosMaximo());
        entity.setQuantidadeNumerosMinimo(dto.getQuantidadeNumerosMinimo());
        entity.setQuantidadeNumerosMaximoParaGanhar(dto.getQuantidadeNumerosMaximoParaGanhar());
        entity.setQuantidadeNumerosMinimoParaGanhar(dto.getQuantidadeNumerosMinimoParaGanhar());
    }

    public TipoJogoDTO get(String tipoJogo) {
        Optional<TipoJogoEntity> optional = tipoJogoRepository.findByNome(tipoJogo.toUpperCase());
        TipoJogoEntity tipoJogoEntity = optional.orElseThrow(() -> new ResourceNotFoundException("Tipo jogo não cadastrado: " + tipoJogo));
        return get(tipoJogoEntity.getId());
    }

    public TipoJogoEntity getTipoJogo(String tipoJogo) {
        Optional<TipoJogoEntity> optional = tipoJogoRepository.findByNome(tipoJogo.toUpperCase());
        return optional.orElseThrow(() -> new ResourceNotFoundException("Tipo jogo não cadastrado: " + tipoJogo));
    }
}
