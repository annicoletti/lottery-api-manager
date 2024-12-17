package br.com.nicoletti.loto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.nicoletti.loto.beans.dto.JogoDTO;
import br.com.nicoletti.loto.beans.entities.JogoEntity;
import br.com.nicoletti.loto.beans.enuns.TipoJogoEnum;

@Service
@EnableScheduling
public class UpdateLotoService {

    private final Logger logger = LoggerFactory.getLogger(UpdateLotoService.class);

    private static final int HOUR_IN_MILLIS = 3600000;

    @Autowired
    private RestService restService;

    @Autowired
    private JogoService jogoService;

    @Autowired
    private ApostaService apostaService;

    @Scheduled(fixedDelay = HOUR_IN_MILLIS)
    public void fullDataUpdate() {
        logger.info("Atualizando dados dos sorteios");
        refreshLottery(TipoJogoEnum.MEGASENA);
        refreshLottery(TipoJogoEnum.LOTOFACIL);

        checkBets();
        tryLoadErrorContest();
    }

    private void checkBets() {
        try {
            apostaService.verificaApostasRealizada();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void tryLoadErrorContest() {
        try {
            jogoService.checkingErrorCases();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshLottery(TipoJogoEnum tipoJogo) {
        try {
            // Pesquisa ultimo sorteio realizado
            JogoDTO lastResultDto = jogoService.getLotoResultOnline(tipoJogo, null);

            // Recupera o ultimo resultado salvo
            JogoEntity ultimoJogoEntity = jogoService.getLastLotoResultSaved(tipoJogo.name());

            // Verifico quantos jogos precisa atualizar
            Integer numeroConcursoSalvo = ultimoJogoEntity.getNumeroConcurso() == null ? 0 : ultimoJogoEntity.getNumeroConcurso();
            Integer totalJogosRestantes = (lastResultDto.getNumeroConcurso() - numeroConcursoSalvo);

            logger.info("Atualizando {} registros", totalJogosRestantes);
            Integer concurso = ultimoJogoEntity.getNumeroConcursoProximo() == null ? 1 : ultimoJogoEntity.getNumeroConcursoProximo();
            while (concurso <= lastResultDto.getNumeroConcurso()) {
                logger.info("Tipo jogo:{} numero concurso:{} restantes:{}", tipoJogo, concurso, totalJogosRestantes);
                jogoService.downloadResultAndPersist(tipoJogo, concurso);
                totalJogosRestantes--;
                concurso++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
