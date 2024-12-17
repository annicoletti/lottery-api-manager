package br.com.nicoletti.loto.beans.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "tb_jogos")
public class JogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "tipoJogoId")
    private TipoJogoEntity tipoJogo = new TipoJogoEntity();

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo", fetch = FetchType.EAGER)
    private Set<DezenaSorteadaEntity> dezenasSorteadas = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo", fetch = FetchType.EAGER)
    private Set<PremioEntity> premios = new HashSet<>();

}
