package br.com.nicoletti.loto.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.nicoletti.loto.beans.dto.DezenaSorteadaDTO;
import br.com.nicoletti.loto.beans.dto.JogoDTO;
import br.com.nicoletti.loto.beans.dto.PremioDTO;
import br.com.nicoletti.loto.beans.entities.DezenaSorteadaEntity;
import br.com.nicoletti.loto.beans.entities.ErroLotoEntity;
import br.com.nicoletti.loto.beans.entities.JogoEntity;
import br.com.nicoletti.loto.beans.entities.PremioEntity;
import br.com.nicoletti.loto.beans.entities.TipoJogoEntity;
import br.com.nicoletti.loto.beans.enuns.TipoJogoEnum;
import br.com.nicoletti.loto.exceptions.ResourceNotFoundException;
import br.com.nicoletti.loto.exceptions.RestServiceException;
import br.com.nicoletti.loto.repositories.DezenaSorteadaRepository;
import br.com.nicoletti.loto.repositories.ErroLotoRepository;
import br.com.nicoletti.loto.repositories.JogoRepository;
import br.com.nicoletti.loto.utils.LotoUtils;

@Service
public class JogoService {

    private final Logger logger = LoggerFactory.getLogger(JogoService.class);

    private static final String SERVER = "https://servicebus2.caixa.gov.br";

    private static final String PATH = "/portaldeloterias/api/%s/";

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private JogoRepository repository;

    @Autowired
    private DezenaSorteadaRepository dezenaSorteadaRepository;

    @Autowired
    private TipoJogoService tipoJogoService;

    @Autowired
    private RestService restService;

    @Autowired
    private ErroLotoRepository erroLotoRepository;

    @Autowired
    private ApostaService apostaService;

    @Transactional
    public Page<JogoDTO> list(PageRequest pageRequest) {
        Page<JogoEntity> pageable = repository.findAll(pageRequest);
        return pageable.map(JogoDTO::new);
    }

    @Transactional
    public JogoDTO create(JogoDTO dto) {
        JogoEntity entity = new JogoEntity();
        parseDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new JogoDTO(entity);
    }

    @Transactional
    public JogoDTO get(Long id) {
        Optional<JogoEntity> optional = repository.findById(id);
        JogoEntity sorteioEntity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new JogoDTO(sorteioEntity);
    }

    @Transactional
    public JogoDTO getTipoJogoAndConcurso(Integer concurso, String tipoJogo) {
        TipoJogoEntity tipoJogoEntity = tipoJogoService.getTipoJogo(tipoJogo);
        Optional<JogoEntity> optional = repository.findByNumeroConcursoAndTipoJogoId(concurso, tipoJogoEntity.getId());
        JogoEntity sorteioEntity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new JogoDTO(sorteioEntity);
    }

    private void parseDtoToEntity(JogoDTO dto, JogoEntity entity) {
        entity.setDataApuracao(dto.getDataApuracao());
        entity.setLocalSorteio(dto.getLocalSorteio());
        entity.setNumeroConcurso(dto.getNumeroConcurso());
        entity.setNumeroConcursoAnterior(dto.getNumeroConcursoAnterior());
        entity.setNumeroConcursoProximo(dto.getNumeroConcursoProximo());
        entity.setAcumulado(dto.getAcumulado());
        entity.setValorArrecadado(dto.getValorArrecadado());
        entity.setValorAcumuladoProximoConcurso(dto.getValorAcumuladoProximoConcurso());
        entity.setValorEstimadoProximoConcurso(dto.getValorEstimadoProximoConcurso());

        TipoJogoEntity tipoJogoEntity = tipoJogoService.getTipoJogo(dto.getTipoJogo());
        entity.setTipoJogo(tipoJogoEntity);

        for (DezenaSorteadaDTO dezena : dto.getDezenas()) {
            DezenaSorteadaEntity dezenaEntity = new DezenaSorteadaEntity();
            dezenaEntity.setId(dezena.getId());
            dezenaEntity.setJogo(entity);
            dezenaEntity.setDezena(dezena.getDezena());
            dezenaEntity.setOrdem(dezena.getOrdem());
            entity.getDezenasSorteadas().add(dezenaEntity);
        }

        for (PremioDTO premio : dto.getPremios()) {
            PremioEntity premioEntity = new PremioEntity();
            premioEntity.setId(premio.getId());
            premioEntity.setJogo(entity);
            premioEntity.setFaixa(premio.getFaixa());
            premioEntity.setDescricao(premio.getDescricao());
            premioEntity.setValorPremio(premio.getValorPremio());
            entity.getPremios().add(premioEntity);
        }

    }

    private JogoDTO parseJsonToDto(JSONObject jsonObject) {
        try {
            JogoDTO dto = new JogoDTO();

            List<PremioDTO> premioDTOS = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("listaRateioPremio");
            jsonArray.forEach(item -> {
                JSONObject jsonPrize = (JSONObject) item;
                PremioDTO to = new PremioDTO();
                to.setDescricao(jsonPrize.getString("descricaoFaixa"));
                to.setNumeroGanhadores(jsonPrize.getInt("numeroDeGanhadores"));
                to.setValorPremio(jsonPrize.getBigDecimal("valorPremio"));
                to.setFaixa(jsonPrize.getInt("faixa"));
                premioDTOS.add(to);
            });

            dto.setDataApuracao(SIMPLE_DATE_FORMAT.parse(jsonObject.getString("dataApuracao")).toInstant());
            dto.setLocalSorteio(jsonObject.getString("localSorteio").trim());
            dto.setNumeroConcurso(jsonObject.getInt("numero"));
            dto.setNumeroConcursoAnterior(jsonObject.getInt("numeroConcursoAnterior"));
            dto.setNumeroConcursoProximo(jsonObject.getInt("numeroConcursoProximo"));
            dto.setAcumulado(jsonObject.getBoolean("acumulado"));
            dto.setValorArrecadado(jsonObject.getBigDecimal("valorArrecadado"));
            dto.setValorAcumuladoProximoConcurso(jsonObject.getBigDecimal("valorAcumuladoProximoConcurso"));
            dto.setValorEstimadoProximoConcurso(jsonObject.getBigDecimal("valorEstimadoProximoConcurso"));

            String tipoJogo = jsonObject.getString("tipoJogo").trim().toUpperCase();
            TipoJogoEnum tipoJogoEnum = TipoJogoEnum.parseBodyCaixa(tipoJogo);

            dto.setTipoJogo(tipoJogoEnum.name());
            dto.setDezenas(parseToDezenaList(jsonObject.getJSONArray("dezenasSorteadasOrdemSorteio")));
            dto.setPremios(premioDTOS);

            return dto;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private List<DezenaSorteadaDTO> parseToDezenaList(JSONArray jsonArray) {
        List<DezenaSorteadaDTO> out = new ArrayList<>();

        int ordem = 1;
        jsonArray.forEach(item -> {
            int dezena = Integer.parseInt((String) item);
            DezenaSorteadaDTO dezenaSorteadaDTO = new DezenaSorteadaDTO();
            dezenaSorteadaDTO.setId(null);
            dezenaSorteadaDTO.setOrdem(ordem);
            dezenaSorteadaDTO.setDezena(dezena);
            out.add(dezenaSorteadaDTO);
        });

        return out;
    }

    private List<Integer> parseToList(JSONArray jsonArray) {
        List<Integer> list = new ArrayList<Integer>();
        jsonArray.forEach(i -> {
            list.add(Integer.parseInt((String) i));
        });
        return list;
    }

    public JogoDTO getLotoResultOnline(TipoJogoEnum tipoJogo, Integer concurso) {
        // Url sem numero do concurso retorna ultimo resultado
        String concursoString = LotoUtils.isNullOrEmpty(concurso) ? "" : String.valueOf(concurso);
        String url = SERVER + String.format(PATH, tipoJogo.name()) + concursoString;
        logger.info("URL REQUEST: {}", url);
        String lastResult = restService.doSimpleGet(url);
        return parseJsonToDto(new JSONObject(lastResult));
    }

    public JogoEntity getLastLotoResultSaved(String tipoJogo) {
        JogoEntity out = new JogoEntity();
        TipoJogoEntity tipJogoEntity = tipoJogoService.getTipoJogo(tipoJogo);
        Optional<JogoEntity> optional = repository.findFirstByTipoJogoIdOrderByNumeroConcursoDesc(tipJogoEntity.getId());
        if (optional.isPresent()) {
            out = optional.get();
        }
        return out;
    }

    public Boolean downloadResultAndPersist(TipoJogoEnum tipoJogo, Integer concurso) {
        Boolean out = Boolean.FALSE;

        try {
            JogoDTO jogoDTO = getLotoResultOnline(tipoJogo, concurso);
            create(jogoDTO);
            out = Boolean.TRUE;

        } catch (Exception e) {

            ErroLotoEntity entity = erroLotoRepository.findByNumeroConcursoAndTipoJogo(concurso, tipoJogo.name());

            if (entity != null) {
                entity.setNumeroConcurso(concurso);
                entity.setDetalhes(e.getMessage());
                entity.setUltimaTentativa(Instant.now());
                entity.setTipoJogo(tipoJogo.name());
                erroLotoRepository.save(entity);

            } else {
                entity = new ErroLotoEntity();
                entity.setNumeroConcurso(concurso);
                entity.setDetalhes(e.getMessage());
                entity.setUltimaTentativa(Instant.now());
                entity.setTipoJogo(tipoJogo.name());
                erroLotoRepository.save(entity);
            }
        }
        return out;
    }

    public void checkingErrorCases() {
        logger.info("Verificação de consulta com erros.");

        List<ErroLotoEntity> allErrors = erroLotoRepository.findAll();
        logger.info("Registros com erros: {}", allErrors.size());

        for (ErroLotoEntity entity : allErrors) {
            TipoJogoEnum tipoJogoEnum = TipoJogoEnum.valueOf(entity.getTipoJogo());
            Boolean status = downloadResultAndPersist(tipoJogoEnum, entity.getNumeroConcurso());

            logger.info("[{}] Resultado da consulta do concurso {} do sorteio da {}.", status, entity.getNumeroConcurso(), tipoJogoEnum.name());
            if (status) {
                erroLotoRepository.delete(entity);
            }
        }
    }

    public JogoDTO getLotoResult(Integer numeroAposta, Long tipoJogoId) {
        Optional<JogoEntity> optional = repository.findByNumeroConcursoAndTipoJogoId(numeroAposta, tipoJogoId);
        if (!optional.isPresent()) {
            throw new RestServiceException("[" + tipoJogoId + "] Resultado do concurso " + numeroAposta + " não encontrado");
        }
        JogoEntity jogoEntity = optional.get();
        return new JogoDTO(jogoEntity);
    }


    public List<JogoDTO> findAllLotoResult(String tipoJogo, String dataInicial) {
        List<JogoDTO> out = new ArrayList<>();

        int numeroPagina = 0;
        int linhasPorPagina = 200;
        String orderBy = "numeroConcurso";
        PageRequest pageRequest = PageRequest.of(numeroPagina, linhasPorPagina, Sort.Direction.ASC, orderBy);
        Page<JogoDTO> resultado = this.list(pageRequest);

        Instant instantInicial = new Date(Long.MIN_VALUE).toInstant();
        if (!LotoUtils.isNullOrEmpty(dataInicial)) {
            instantInicial = LotoUtils.parseStringToInstant(dataInicial);
        }

        int totalPages = resultado.getTotalPages();
        for (int i = 0; i <= totalPages; i++) {
            pageRequest = PageRequest.of(i, linhasPorPagina, Sort.Direction.ASC, orderBy);
            Page<JogoDTO> response = this.list(pageRequest);
            Instant finalInstantInicial = instantInicial;
            response.stream()
                    .filter(item -> item.getTipoJogo().equalsIgnoreCase(tipoJogo) && item.getDataApuracao().isAfter(finalInstantInicial))
                    .forEach(out::add);
        }

        return out;
    }


}
