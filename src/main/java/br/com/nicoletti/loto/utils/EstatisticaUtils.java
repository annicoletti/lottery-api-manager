package br.com.nicoletti.loto.utils;

import br.com.nicoletti.loto.beans.dto.EstatisticaLinhaTO;
import lombok.Data;

import java.util.List;

@Data
public class EstatisticaUtils {

    private Double totalX;
    private Double totalY;
    private Double totalXY;
    private Double totalXpow2;
    private Double mediaX;
    private Double mediaY;
    private Integer n;
    private Double alpha;
    private Double beta;
    private Double sxy;

    private List<EstatisticaLinhaTO> linhas;

    public EstatisticaUtils(List<EstatisticaLinhaTO> linhas) {
        setValues(linhas);
    }

    private void alpha() {
        this.alpha = ((n * totalXY) - (totalX * totalY)) / ((n * totalXpow2) - (Math.pow(totalX, 2)));
        System.out.println("ALPHA: " + alpha);
    }

    private void beta() {
        this.beta = (totalY / n) - (alpha * (totalX / n));
        System.out.println("BETA: " + beta);
    }

    private void setValues(List<EstatisticaLinhaTO> linhas) {
        this.totalX = 0.0;
        this.totalY = 0.0;
        this.totalXY = 0.0;
        this.totalXpow2 = 0.0;
        this.mediaX = 0.0;
        this.mediaY = 0.0;
        this.alpha = 0.0;
        this.beta = 0.0;
        this.n = linhas.size();
        this.sxy = 0.0;
        this.linhas = linhas;

        for (EstatisticaLinhaTO linha : linhas) {
            this.totalX += linha.getX();
            this.totalY += linha.getY();
            this.totalXY += linha.getXY();
            this.totalXpow2 += linha.getXpow2();
        }

        this.mediaX = this.media(totalX, n);
        this.mediaY = this.media(totalY, n);

        this.alpha();
        this.beta();
        this.correlacaoAmostral();

        for (EstatisticaLinhaTO linha : linhas) {
            System.out.println(linha.getY() + " -> " + this.estimativa(linha.getX()));
        }
    }

    private Double media(Double total, Integer n) {
        return total / Double.valueOf(n);
    }

    public double estimativa(Double X) {
        Double Y = (alpha * X) + beta;
        return Y;
    }

    public double estimativaX(Double Y) {
        Double X = (Y - beta) / alpha;
        return X;
    }

    public double desvioPadraoX() {
        Double valor = 0.0;
        for (EstatisticaLinhaTO linha : linhas) {
            valor += Math.pow((linha.getX() - mediaX), 2);
        }
        Double variancia = (valor / (n - 1));
        Double Dp = Math.sqrt(variancia);
        System.out.println("DESVIO PADRAO X: " + Dp);
        return Dp;
    }

    public double desvioPadraoY() {
        Double valor = 0.0;
        for (EstatisticaLinhaTO linha : linhas) {
            valor += Math.pow((linha.getY() - mediaY), 2);
        }
        Double variancia = (valor / (n - 1));
        Double Dp = Math.sqrt(variancia);
        System.out.println("DESVIO PADRAO Y: " + Dp);
        return Dp;
    }

    public double correlacaoAmostral() {
        Double Sxy = (totalXY - ((totalX * totalY) / n)) / (n - 1);
        this.sxy = Sxy;
        System.out.println(">>>>>>>>>>> Sxy: " + Sxy);
        //formula: r = Sxy / soma(x) * soma(y)
        Double r = Sxy / (desvioPadraoX() * desvioPadraoY());
        System.out.println("COEFICIENTE DE CORRELAÇÃO AMOSTRAL: " + r);
        return r;
    }

}
