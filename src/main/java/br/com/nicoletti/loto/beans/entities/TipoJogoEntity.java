package br.com.nicoletti.loto.beans.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "tb_tipos_jogo")
public class TipoJogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    private Integer quantidadeNumerosDisponiveis;
    private Integer quantidadeNumerosMinimo;
    private Integer quantidadeNumerosMaximo;
    private Integer quantidadeNumerosMinimoParaGanhar;
    private Integer quantidadeNumerosMaximoParaGanhar;

}
