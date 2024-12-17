package br.com.nicoletti.loto.beans.dto;

import lombok.Data;

import java.util.Set;

@Data
public class NumerosVerificadoTO {

    private Boolean premiado;
    private Set<Integer> dezenas;

}
