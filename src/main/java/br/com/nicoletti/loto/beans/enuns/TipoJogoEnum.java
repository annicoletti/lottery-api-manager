package br.com.nicoletti.loto.beans.enuns;

import lombok.Getter;

@Getter
public enum TipoJogoEnum {

    MEGASENA("MEGA_SENA"), LOTOFACIL("LOTOFACIL");

    final String bodyCaixa;

    private TipoJogoEnum(String bodyCaixa) {
        this.bodyCaixa = bodyCaixa;
    }

    public static TipoJogoEnum parseBodyCaixa(String bodyCaixa) {
        for (TipoJogoEnum type : TipoJogoEnum.values()) {
            if (type.getBodyCaixa().equalsIgnoreCase(bodyCaixa)) {
                return type;
            }
        }
        throw new RuntimeException("Nome do jogo não existe");
    }

}
