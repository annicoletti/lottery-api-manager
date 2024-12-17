package br.com.nicoletti.loto.beans.dto;

import lombok.Data;

import java.util.Map;

@Data
public class JogoVerificadoTO {

    private Boolean premiado;
    private Integer acertos;
    private Map<Integer, Boolean> resultado;

}
