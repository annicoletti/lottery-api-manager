package br.com.nicoletti.loto.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaLinhaTO {

    private String info;
    private Double x;
    private Double y;

    public Double getXY() {
        return x * y;
    }

    public Double getXpow2() {
        return Math.pow(x, 2);
    }
}
