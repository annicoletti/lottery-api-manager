package br.com.nicoletti.loto.beans.dto;

import br.com.nicoletti.loto.beans.entities.TipoJogoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoJogoDTO {

    private Long id;
    private String nome;
    private Integer quantidadeNumerosDisponiveis;
    private Integer quantidadeNumerosMinimo;
    private Integer quantidadeNumerosMaximo;
    private Integer quantidadeNumerosMinimoParaGanhar;
    private Integer quantidadeNumerosMaximoParaGanhar;

    public TipoJogoDTO(TipoJogoEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.quantidadeNumerosDisponiveis = (entity.getQuantidadeNumerosDisponiveis());
        this.quantidadeNumerosMaximo = entity.getQuantidadeNumerosMaximo();
        this.quantidadeNumerosMinimo = entity.getQuantidadeNumerosMinimo();
        this.quantidadeNumerosMaximoParaGanhar = entity.getQuantidadeNumerosMaximoParaGanhar();
        this.quantidadeNumerosMinimoParaGanhar = entity.getQuantidadeNumerosMinimoParaGanhar();
    }
}
