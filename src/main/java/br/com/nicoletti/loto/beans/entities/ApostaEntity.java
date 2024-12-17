package br.com.nicoletti.loto.beans.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "tb_apostas")
public class ApostaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant dataAposta;
    private Integer numeroConcurso;
    private Integer quantidadeNumeroApostado;
    private BigDecimal valorAposta;
    private Boolean conferido;
    private Boolean premiado;
    private BigDecimal valorPremio;
    private Integer quantidadeAcerto;

    @OneToOne
    @JoinColumn(name = "tipoJogoId")
    private TipoJogoEntity tipoJogo;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aposta", fetch = FetchType.EAGER)
    private Set<DezenaEntity> dezenas = new HashSet<>();

}
