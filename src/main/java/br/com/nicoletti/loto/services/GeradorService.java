package br.com.nicoletti.loto.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nicoletti.loto.beans.dto.ApostaAutomaticaDTO;
import br.com.nicoletti.loto.beans.dto.EstatisticaLinhaTO;
import br.com.nicoletti.loto.beans.dto.JogoDTO;
import br.com.nicoletti.loto.beans.dto.ProgressaoLinearTO;
import br.com.nicoletti.loto.beans.dto.TipoJogoDTO;

@Service
public class GeradorService {

    private final Logger logger = LoggerFactory.getLogger(GeradorService.class);

    @Autowired
    private JogoService jogoService;

    @Autowired
    private TipoJogoService tipoJogoService;

    @Autowired
    private EstatisticaService estatisticaService;

    @Autowired
    private ApostaService apostaService;

    public List<Set<Integer>> createAuto(ApostaAutomaticaDTO dto) {

//        List<JogoDTO> todosJogos = jogoService.getAllLotoResult(dto.getTipoJogo());
//        Map<Integer, Integer> quantidade = somaTotaisNumerosSorteados(todosJogos);
//        logger.info("NUMEROS: {}", quantidade);

        List<Set<Integer>> numerosGerados = geradorNumerosAleatorio(dto.getTipoJogo(), dto.getQuantidadeNumeros(), dto.getQuantidadeJogos());
        return numerosGerados;
    }

    private Map<Integer, Integer> somaTotaisNumerosSorteados(List<JogoDTO> jogos) {
        Map<Integer, Integer> quantidade = new TreeMap<>();
        jogos.forEach(jogo -> jogo.getDezenas().forEach(dezena -> quantidade.merge(dezena.getDezena(), 1, Integer::sum)));
        return quantidade;
    }

    public List<Set<Integer>> geradorNumerosAleatorio(String tipoJogo, Integer quantidadeNumeros, Integer quantidade) {
        List<Set<Integer>> out = new ArrayList<>();
        Random random = new Random();

        TipoJogoDTO tipoJogoDTO = tipoJogoService.get(tipoJogo);
        int maximo = tipoJogoDTO.getQuantidadeNumerosDisponiveis();

        while (out.size() < quantidade) {
            //Adiciona numeros na até atingir a quantidade do jogo
            Set<Integer> numeros = new TreeSet<>();
            while (numeros.size() < quantidadeNumeros) {
                int rand = random.nextInt(maximo) + 1;
                numeros.add(rand);
            }

            boolean premiado = apostaService.verificaSeNumeroJaFoiSorteado(numeros, tipoJogoDTO);
            if (!premiado) {
                out.add(numeros);
            }
        }

        logger.info("Números gerados: {}", out);
        return out;
    }


    public List<ProgressaoLinearTO> tendencia(ApostaAutomaticaDTO dto) {
        List<JogoDTO> allLotoResult = jogoService.findAllLotoResult(dto.getTipoJogo(), dto.getDataInicioCalculo());
        logger.info("QUANTIDADE SORTEIOS: {}", allLotoResult.size());

        // agrupamento de jogos em grupos
        Map<Integer, Map<Integer, Integer>> dadosAgrupado = new HashMap<>(); // TROCAR PARA MAP ??

        int grupo = 30, contadorGrupo = 0;
        for (int i = 0, j = grupo; i <= allLotoResult.size(); i += grupo, j += grupo) {
            List<JogoDTO> jogoDTOS = new ArrayList<>();
            if (j <= allLotoResult.size()) {
                jogoDTOS = allLotoResult.subList(i, j);
                dadosAgrupado.put(contadorGrupo++, somaTotaisNumerosSorteados(jogoDTOS));
            }
//            Ignora o resto por enquanto
//            else {
//                jogoDTOS = allLotoResult.subList(i, allLotoResult.size());
//            }
//            dadosAgrupado.put(contadorGrupo++, somaTotaisNumerosSorteados(jogoDTOS));
        }


        logger.info("===================================================");
        logger.info("===================================================");
        logger.info("===================================================");
        logger.info("AGRUPADO: {}", new JSONObject(dadosAgrupado));
        logger.info("===================================================");
        logger.info("===================================================");
        logger.info("===================================================");

        // realiza a montagem dos dados
        Map<Integer, List<EstatisticaLinhaTO>> dados = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Integer>> entryMap : dadosAgrupado.entrySet()) {
            Integer i = entryMap.getKey();

            double acumuladoY = 0.0;
            for (Map.Entry<Integer, Integer> entry : entryMap.getValue().entrySet()) {
                double x = entry.getKey();
                acumuladoY += entry.getValue();
                double g = Double.sum(i, 1) * grupo;

                List<EstatisticaLinhaTO> estatisticaLinhas = dados.get(entry.getKey());
                if (estatisticaLinhas == null) {
                    estatisticaLinhas = new ArrayList<>();
                }

                EstatisticaLinhaTO to = new EstatisticaLinhaTO(String.valueOf(x), g, acumuladoY);
                estatisticaLinhas.add(to);
                dados.put(entry.getKey(), estatisticaLinhas);
            }
        }

        logger.error("VERIFICANDO MAPEAMENTO - PROVISORIO");
        logger.info("DADOS: {}", new JSONObject(dados).toString(4));

        List<ProgressaoLinearTO> tendencia = estatisticaService.tendencia(dados);

        return tendencia;
    }
}
