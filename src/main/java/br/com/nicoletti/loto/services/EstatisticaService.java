package br.com.nicoletti.loto.services;

import br.com.nicoletti.loto.beans.dto.EstatisticaLinhaTO;
import br.com.nicoletti.loto.beans.dto.ProgressaoLinearTO;
import br.com.nicoletti.loto.utils.EstatisticaUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EstatisticaService {


    public List<ProgressaoLinearTO> tendencia(Map<Integer, List<EstatisticaLinhaTO>> dados) {
        List<ProgressaoLinearTO> out = new ArrayList<>();

        for (Map.Entry<Integer, List<EstatisticaLinhaTO>> entry : dados.entrySet()) {
            EstatisticaUtils utils = new EstatisticaUtils(entry.getValue());

            ProgressaoLinearTO to = new ProgressaoLinearTO();
            to.setLegenda(String.valueOf(entry.getKey()));
            to.setDesvioPadraoX(utils.desvioPadraoX());
            to.setDesvioPadraoY(utils.desvioPadraoY());
            to.setCoeficienteCorrelacaoAmostral(utils.correlacaoAmostral());
            to.setAlpha(utils.getAlpha());
            to.setBeta(utils.getBeta());
            to.setSxy(utils.getSxy());
            out.add(to);
        }

        return out;
    }
}
