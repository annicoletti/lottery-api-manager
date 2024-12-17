package br.com.nicoletti.loto.beans.dto;

import lombok.Data;

@Data
public class ProgressaoLinearTO {

    private String legenda;
    private Double alpha;
    private Double beta;
    private Double desvioPadraoX;
    private Double desvioPadraoY;
    private Double sxy;
    private Double coeficienteCorrelacaoAmostral;

}
