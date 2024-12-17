package br.com.nicoletti.loto.services;

import br.com.nicoletti.loto.beans.dto.ApostaDTO;
import br.com.nicoletti.loto.beans.dto.JogoDTO;
import br.com.nicoletti.loto.beans.dto.JogoVerificadoTO;
import br.com.nicoletti.loto.beans.dto.NumerosVerificadoTO;
import br.com.nicoletti.loto.beans.dto.PremioDTO;
import br.com.nicoletti.loto.beans.dto.TipoJogoDTO;
import br.com.nicoletti.loto.beans.entities.ApostaEntity;
import br.com.nicoletti.loto.beans.entities.DezenaEntity;
import br.com.nicoletti.loto.beans.entities.JogoEntity;
import br.com.nicoletti.loto.beans.entities.TipoJogoEntity;
import br.com.nicoletti.loto.exceptions.DatabaseException;
import br.com.nicoletti.loto.exceptions.ResourceNotFoundException;
import br.com.nicoletti.loto.repositories.ApostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Service
public class ApostaService {

    private final Logger logger = LoggerFactory.getLogger(ApostaService.class);

    @Autowired
    private ApostaRepository repository;

    @Autowired
    private TipoJogoService tipoJogoService;

    @Autowired
    private JogoService jogoService;

    @Transactional
    public Page<ApostaDTO> list(PageRequest pageRequest) {
        Page<ApostaEntity> pageable = repository.findAll(pageRequest);
        return pageable.map(ApostaDTO::new);
    }

    @Transactional
    public ApostaDTO get(Long id) {
        Optional<ApostaEntity> optional = repository.findById(id);
        ApostaEntity entity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new ApostaDTO(entity);
    }

    @Transactional
    public ApostaDTO create(ApostaDTO dto) {
        ApostaEntity entity = new ApostaEntity();
        parseDtoToEntity(dto, entity);
        repository.save(entity);
        return new ApostaDTO(entity);
    }

    @Transactional
    public ApostaDTO update(Long id, ApostaDTO dto) {
        try {
            ApostaEntity entity = repository.getOne(id);
            parseDtoToEntity(dto, entity);
            repository.save(entity);
            return new ApostaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            ApostaDTO dto = this.get(id);
            repository.deleteById(dto.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void parseDtoToEntity(ApostaDTO dto, ApostaEntity entity) {
        entity.setId(dto.getId());
        entity.setDataAposta(Instant.now());
        entity.setNumeroConcurso(dto.getNumeroConcurso());
        entity.setQuantidadeNumeroApostado(dto.getQuantidadeNumeroApostado());
        entity.setValorAposta(dto.getValorAposta());
        entity.setConferido(dto.getConferido());
        entity.setPremiado(dto.getPremiado());
        entity.setValorPremio(dto.getValorPremio());
        entity.setQuantidadeAcerto(dto.getQuantidadeAcerto());

        entity.setTipoJogo(tipoJogoService.getTipoJogo(dto.getTipoJogo()));

        for (Integer dezena : dto.getDezenas()) {
            DezenaEntity dezenaEntity = new DezenaEntity();
            dezenaEntity.setId(null);
            dezenaEntity.setDezena(dezena);
            dezenaEntity.setAposta(entity);
            entity.getDezenas().add(dezenaEntity);
        }
    }

    public List<ApostaDTO> listAll() {
        List<ApostaEntity> entities = repository.findAll();
        return entities.stream().map(ApostaDTO::new).toList();
    }

    public void verificaApostasRealizada() {
        List<ApostaEntity> apostaEntities = repository.findAll();
        logger.info("VERIFICANDO JOGOS FEITOS - TOTAL {}", apostaEntities.size());

        for (ApostaEntity aposta : apostaEntities) {
            Boolean conferred = aposta.getConferido();

            if (!conferred) {
                TipoJogoEntity tipoJogo = aposta.getTipoJogo();

                JogoDTO jogoDTO = jogoService.getLotoResult(aposta.getNumeroConcurso(), tipoJogo.getId());
                Set<Integer> dezenasResultado = new HashSet<>();
                jogoDTO.getDezenas().forEach(numeros -> dezenasResultado.add(numeros.getDezena()));

                Set<DezenaEntity> dezenas = aposta.getDezenas();
                Integer acertos = 0;
                for (DezenaEntity dezena : dezenas) {
                    if (dezenasResultado.contains(dezena.getDezena())) {
                        dezena.setAcerto(Boolean.TRUE);
                        acertos++;
                    }
                }

                Integer quantidadeNumerosMinimoParaGanhar = tipoJogo.getQuantidadeNumerosMinimoParaGanhar();
                BigDecimal valorPremio = BigDecimal.ZERO;
                Boolean premiado = Boolean.FALSE;
                if (acertos >= quantidadeNumerosMinimoParaGanhar) {
                    premiado = Boolean.TRUE;
                    List<PremioDTO> premios = jogoDTO.getPremios();
                    for (PremioDTO premio : premios) {
                        if (premio.getDescricao().contains(String.valueOf(acertos))) {
                            logger.info("{} - PREMIO: {}", premio.getDescricao(), premio.getValorPremio());
                            valorPremio = premio.getValorPremio();
                        }
                    }
                }

                aposta.setConferido(Boolean.TRUE);
                aposta.setPremiado(premiado);
                aposta.setValorPremio(valorPremio);
                aposta.setQuantidadeAcerto(acertos);
                repository.save(aposta);

            }
        }
    }

    public List<NumerosVerificadoTO> confereNumeroApostaHistorico(String tipoJogo, List<Set<Integer>> dezenas) {
        List<NumerosVerificadoTO> out = new ArrayList<>();

        TipoJogoDTO tipoJogoDTO = tipoJogoService.get(tipoJogo);
        System.out.println("################## NUMEROS: " + dezenas);

        for (Set<Integer> numeros : dezenas) {
            boolean vencedor = verificaSeNumeroJaFoiSorteado(numeros, tipoJogoDTO);
            NumerosVerificadoTO numerosVerificadoTO = new NumerosVerificadoTO();
            numerosVerificadoTO.setPremiado(vencedor);
            numerosVerificadoTO.setDezenas(numeros);
            out.add(numerosVerificadoTO);
        }
        return out;
    }

    public boolean verificaSeNumeroJaFoiSorteado(Set<Integer> numeros, TipoJogoDTO tipoJogoDTO) {
        logger.info("Conferindo se numero escolhido já foi sorteado");
        JogoEntity lastLotoResultSaved = jogoService.getLastLotoResultSaved(tipoJogoDTO.getNome());
        Integer ultimoConcurso = lastLotoResultSaved.getNumeroConcurso();

        for (int i = 1; i <= ultimoConcurso; i++) {
            JogoVerificadoTO jogoVerificadoTO = this.confereAposta(numeros, i, tipoJogoDTO);
            if (jogoVerificadoTO.getPremiado()) {
                logger.info("Numeros {} já foram contemplados no sorteio {}", numeros, i);
                return true;
            }
        }
        return false;
    }

    public JogoVerificadoTO confereAposta(Set<Integer> numerosApostado, Integer numeroConcurso, TipoJogoDTO tipoJogoDTO) {
        logger.info("Conferindo {} concurso: {}", tipoJogoDTO.getNome(), numeroConcurso);
        JogoVerificadoTO out = new JogoVerificadoTO();
        out.setPremiado(Boolean.FALSE);

        JogoDTO resultadoJogo = jogoService.getLotoResult(numeroConcurso, tipoJogoDTO.getId());
        Set<Integer> resultadoJogoNumeros = new TreeSet<>();
        resultadoJogo.getDezenas().forEach(t -> resultadoJogoNumeros.add(t.getDezena()));

        int correct = 0;
        Map<Integer, Boolean> checkResult = new TreeMap<>();
        for (Integer numero : resultadoJogoNumeros) {
            if (numerosApostado.contains(numero)) {
                checkResult.put(numero, Boolean.TRUE);
                correct++;
            } else {
                checkResult.put(numero, Boolean.FALSE);
            }
        }

        if (correct >= tipoJogoDTO.getQuantidadeNumerosMaximoParaGanhar()) {
            out.setPremiado(Boolean.TRUE);
        }

        out.setResultado(checkResult);
        out.setAcertos(correct);

        return out;
    }


}
