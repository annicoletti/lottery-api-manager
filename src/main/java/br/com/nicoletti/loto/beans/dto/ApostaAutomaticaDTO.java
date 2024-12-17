package br.com.nicoletti.loto.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApostaAutomaticaDTO {

    private String tipoJogo;
    private Integer quantidadeJogos;
    private Integer quantidadeNumeros;
    private String dataInicioCalculo;

    private ApostaDTO aposta;

}
