package br.com.nicoletti.loto.beans.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.com.nicoletti.loto.beans.entities.DezenaSorteadaEntity;
import br.com.nicoletti.loto.beans.entities.JogoEntity;
import br.com.nicoletti.loto.beans.entities.PremioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogoDTO {

    private Long id;
    private Instant dataApuracao;
    private String localSorteio;
    private Integer numeroConcurso;
    private Integer numeroConcursoAnterior;
    private Integer numeroConcursoProximo;
    private Boolean acumulado;
    private BigDecimal valorArrecadado;
    private BigDecimal valorAcumuladoProximoConcurso;
    private BigDecimal valorEstimadoProximoConcurso;
    private String tipoJogo;
    private List<DezenaSorteadaDTO> dezenas = new ArrayList<>();
    private List<PremioDTO> premios = new ArrayList<>();


    public JogoDTO(JogoEntity entity) {
        this.id = entity.getId();
        this.dataApuracao = entity.getDataApuracao();
        this.localSorteio = entity.getLocalSorteio();
        this.numeroConcurso = entity.getNumeroConcurso();
        this.numeroConcursoAnterior = entity.getNumeroConcursoAnterior();
        this.numeroConcursoProximo = entity.getNumeroConcursoProximo();
        this.acumulado = entity.getAcumulado();
        this.valorArrecadado = entity.getValorArrecadado();
        this.valorAcumuladoProximoConcurso = entity.getValorAcumuladoProximoConcurso();
        this.valorEstimadoProximoConcurso = entity.getValorEstimadoProximoConcurso();
        this.setTipoJogo(entity.getTipoJogo().getNome());

        for (DezenaSorteadaEntity dezenasSorteada : entity.getDezenasSorteadas()) {
            this.dezenas.add(new DezenaSorteadaDTO(dezenasSorteada));
        }

        for (PremioEntity premioEntity : entity.getPremios()) {
            this.premios.add(new PremioDTO(premioEntity));
        }
    }

}
