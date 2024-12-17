package br.com.nicoletti.loto.beans.dto;

import java.math.BigDecimal;

import br.com.nicoletti.loto.beans.entities.PremioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremioDTO {

    private Long id;
    private String descricao;
    private Integer faixa;
    private Integer numeroGanhadores;
    private BigDecimal valorPremio;

    public PremioDTO(PremioEntity entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.faixa = entity.getFaixa();
        this.numeroGanhadores = entity.getNumeroGanhadores();
        this.valorPremio = entity.getValorPremio();
    }
}
