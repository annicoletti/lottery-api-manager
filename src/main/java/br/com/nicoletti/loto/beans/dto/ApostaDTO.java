package br.com.nicoletti.loto.beans.dto;

import br.com.nicoletti.loto.beans.entities.ApostaEntity;
import br.com.nicoletti.loto.beans.entities.DezenaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApostaDTO {

    private Long id;
    private Instant dataAposta;
    private Integer numeroConcurso;
    private Integer quantidadeNumeroApostado;
    private BigDecimal valorAposta;
    private Boolean conferido;
    private Boolean premiado;
    private BigDecimal valorPremio;
    private String tipoJogo;
    private Integer quantidadeAcerto;
    private List<Integer> dezenas = new ArrayList<>();

    public ApostaDTO(ApostaEntity entity) {
        this.id = entity.getId();
        this.dataAposta = entity.getDataAposta();
        this.numeroConcurso = entity.getNumeroConcurso();
        this.quantidadeNumeroApostado = entity.getQuantidadeNumeroApostado();
        this.valorAposta = entity.getValorAposta();
        this.conferido = entity.getConferido();
        this.premiado = entity.getPremiado();
        this.valorPremio = entity.getValorPremio();
        this.tipoJogo = entity.getTipoJogo().getNome();
        this.quantidadeAcerto = entity.getQuantidadeAcerto();

        for (DezenaEntity dezenaEntity : entity.getDezenas()) {
            dezenas.add(dezenaEntity.getDezena());
        }

    }
}
