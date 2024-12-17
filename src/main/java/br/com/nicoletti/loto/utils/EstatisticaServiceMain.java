package br.com.nicoletti.loto.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.nicoletti.loto.beans.dto.EstatisticaLinhaTO;

public class EstatisticaServiceMain {

    // RETA DE REGRESSAO: Y = alfa * X + beta
    // ALFA = Coeficiente dependente
    // BETA = Coeficiente idependente
    // Y = Variável dependente (depende de X)
    // X = Variável idependente

    public static void main(String[] args) {
        System.out.println("####################################################");
        System.out.println("TESTE 01 - RESPONDE AI");
        EstatisticaLinhaTO norte = new EstatisticaLinhaTO("norte", 35.6, 12.7);
        EstatisticaLinhaTO nordeste = new EstatisticaLinhaTO("nordeste", 59.0, 29.4);
        EstatisticaLinhaTO sudeste = new EstatisticaLinhaTO("sudeste", 25.2, 8.6);
        EstatisticaLinhaTO sul = new EstatisticaLinhaTO("sul", 22.5, 8.3);
        EstatisticaLinhaTO centroOeste = new EstatisticaLinhaTO("centro oeste", 24.41, 12.4);
        List<EstatisticaLinhaTO> respondeAi = Arrays.asList(new EstatisticaLinhaTO[]{norte, nordeste, sudeste, sul, centroOeste});
        new EstatisticaUtils(respondeAi);


        System.out.println("####################################################");
        System.out.println("TESTE 02 - RESPONDE AI - CORRELACAO");
        List<EstatisticaLinhaTO> respondeAi2 = new ArrayList<>();
        respondeAi2.add(new EstatisticaLinhaTO("1", 31.0, 2280.0));
        respondeAi2.add(new EstatisticaLinhaTO("2", 32.0, 1540.0));
        respondeAi2.add(new EstatisticaLinhaTO("3", 33.0, 1030.0));
        respondeAi2.add(new EstatisticaLinhaTO("4", 34.0, 850.0));
        respondeAi2.add(new EstatisticaLinhaTO("5", 35.0, 485.0));
        respondeAi2.add(new EstatisticaLinhaTO("6", 36.0, 32.0));
        new EstatisticaUtils(respondeAi2);

        System.out.println("####################################################");
        System.out.println("TESTE 03 - SÓ NÚMEROS");
        List<EstatisticaLinhaTO> soNumeros = new ArrayList<>();
        soNumeros.add(new EstatisticaLinhaTO("a", 1.0, 3.0));
        soNumeros.add(new EstatisticaLinhaTO("b", 2.0, 7.0));
        soNumeros.add(new EstatisticaLinhaTO("c", 3.0, 5.0));
        soNumeros.add(new EstatisticaLinhaTO("d", 4.0, 11.0));
        soNumeros.add(new EstatisticaLinhaTO("e", 5.0, 14.0));
        new EstatisticaUtils(soNumeros);

        System.out.println("####################################################");
        System.out.println("TESTE 04 - LOTERIA 1");
        List<EstatisticaLinhaTO> loterias = new ArrayList<>();
        loterias.add(new EstatisticaLinhaTO("jan", 30.0, 10.0));
        loterias.add(new EstatisticaLinhaTO("fev", 60.0, 15.0));
        loterias.add(new EstatisticaLinhaTO("mar", 90.0, 30.0));
        loterias.add(new EstatisticaLinhaTO("abr", 120.0, 50.0));
        loterias.add(new EstatisticaLinhaTO("mai", 150.0, 75.0));
        new EstatisticaUtils(loterias);

        System.out.println("####################################################");
        System.out.println("TESTE 05 - LOTERIA 2");
        List<EstatisticaLinhaTO> loterias2 = new ArrayList<>();
        loterias2.add(new EstatisticaLinhaTO("jan", 1.0, 15.0));
        loterias2.add(new EstatisticaLinhaTO("fev", 2.0, 20.0));
        loterias2.add(new EstatisticaLinhaTO("mar", 3.0, 30.0));
        loterias2.add(new EstatisticaLinhaTO("abr", 4.0, 10.0));
        loterias2.add(new EstatisticaLinhaTO("mai", 5.0, 10.0));
        new EstatisticaUtils(loterias2);

        System.out.println("####################################################");
        System.out.println("TESTE 06 - LOTERIA 3");
        List<EstatisticaLinhaTO> loterias3 = new ArrayList<>();
        loterias3.add(new EstatisticaLinhaTO("jan", 30.0, 10.0));
        loterias3.add(new EstatisticaLinhaTO("fev", 60.0, 20.0));
        loterias3.add(new EstatisticaLinhaTO("mar", 90.0, 30.0));
        loterias3.add(new EstatisticaLinhaTO("abr", 120.0, 40.0));
        loterias3.add(new EstatisticaLinhaTO("mai", 150.0, 50.0));
        EstatisticaUtils table = new EstatisticaUtils(loterias3);
        double Y1 = table.estimativa(30.0);
        double X1 = table.estimativaX(Y1);
        System.out.println("X1: " + X1 + " -> Y1: " + Y1);

        double Y2 = table.estimativa(0.0);
        double X2 = table.estimativaX(Y2);
        System.out.println("X2: " + X2 + " -> Y2: " + Y2);


        System.out.println("####################################################");
        System.out.println("TESTE 07 - LOTERIA 4");
        List<EstatisticaLinhaTO> loterias4 = new ArrayList<>();
        loterias4.add(new EstatisticaLinhaTO("22", 1.0, 1.0));
        loterias4.add(new EstatisticaLinhaTO("22", 2.0, 4.0));
        loterias4.add(new EstatisticaLinhaTO("22", 3.0, 1.0));
        loterias4.add(new EstatisticaLinhaTO("22", 4.0, 1.0));
        new EstatisticaUtils(loterias4);

        System.out.println("####################################################");
        System.out.println("####################################################");
        System.out.println("####################################################");
        System.out.println("####################################################");
        System.out.println("TESTE 08 - LOTERIA 5");
        List<EstatisticaLinhaTO> loterias5 = new ArrayList<>();
        loterias5.add(new EstatisticaLinhaTO("22", 5.0, 1.0));
        loterias5.add(new EstatisticaLinhaTO("22", 10.0, 5.0));
        loterias5.add(new EstatisticaLinhaTO("22", 15.0, 6.0));
        loterias5.add(new EstatisticaLinhaTO("22", 17.0, 7.0));
        new EstatisticaUtils(loterias5);

        System.out.println("TESTE 09 - LOTERIA 6");
        List<EstatisticaLinhaTO> loterias6 = new ArrayList<>();
        loterias6.add(new EstatisticaLinhaTO("23", 5.0, 3.0));
        loterias6.add(new EstatisticaLinhaTO("23", 10.0, 5.0));
        loterias6.add(new EstatisticaLinhaTO("23", 15.0, 7.0));
        loterias6.add(new EstatisticaLinhaTO("23", 17.0, 9.0));
        loterias6.add(new EstatisticaLinhaTO("23", 25.0, 10.0));
        new EstatisticaUtils(loterias6);

//        teste1();
//        teste2();
    }

    private static void teste2() {
        EstatisticaLinhaTO janeiro = new EstatisticaLinhaTO("janeiro", 1.0, 1.0);
        EstatisticaLinhaTO fevereiro = new EstatisticaLinhaTO("fevereiro", 2.0, 1.0);
        EstatisticaLinhaTO março = new EstatisticaLinhaTO("março", 3.0, 1.0);
    }

    private static void teste1() {

        Double[] colunaX = new Double[]{1.0, 6.0, 10.0};    // Valores
        Double[] colunay = new Double[]{1.0, 2.0, 3.0};     // Mes

//        Double[] colunaX = new Double[]{1.0, 6.0, 10.0, 0.0, 0.0};
//        Double[] colunay = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};

        // PASSO 1
        Double a = encontrarValorA(colunaX, colunay);

        // PASSO 2
        Double b = encontrarValorB(colunaX, colunay);

        // PASSO 3
//        Double tendenciaMes1 = linear(a, b, 1.0, 1.0);
//        Double tendenciaMes2 = linear(a, b, 6.0, 2.0);
//        Double tendenciaMes3 = linear(a, b, 10.0, 3.0);
//        Double tendenciaMes4 = tendencia(a, b, 0.0, 4.0);
//        Double tendenciaMes5 = tendencia(a, b, 0.0, 5.0);
    }

    private static Double linear(Double a, Double b, Double[] colunaX, Double[] colunaY) {
        // formula: β = ÿ - α*ẍ

        Double mediaX = somaColuna(colunaX) / colunaX.length;
        Double mediaY = somaColuna(colunaY) / colunaX.length;

        Double beta = mediaY - (a * mediaX);
        return beta;
    }

    /**
     * @param a
     * @param b
     * @param y - constante
     * @return
     */
    private static Double tendencia(Double a, Double b, Double x, Double y) {
        // formula: x = (y-a) / b
        Double r = (y - a) / b;
        System.out.println(y + " tendencia: " + r);
        return r;
    }

    /**
     * Step 1
     */
    private static Double encontrarValorA(Double[] colunaX, Double[] colunay) {

        // N1 =  Σx² * Σy
        Double somaXAoQuadrado = somaColunaAoQuadrado(colunaX);
        Double somaY = somaColuna(colunay);
        Double n1 = somaXAoQuadrado * somaY;
        System.out.println("Linha 1: " + n1);

        // N2 = Σx * Σ(x * y)
        Double somaX = somaColuna(colunaX);
        Double somaXYmultiplicando = somaColunasMultiplicando(colunaX, colunay);
        Double n2 = somaX * somaXYmultiplicando;
        System.out.println("Linha 2: " + n2);

        // N3 = n * Σx²
        Integer quantidadeNumerosColuna = colunaX.length;
        Double n3 = quantidadeNumerosColuna * somaXAoQuadrado;
        System.out.println("Linha 3: " + n3);

        // N4 = (Σx)²
        Double n4 = Math.pow(somaX, 2);
        System.out.println("Linha 4: " + n4);

        //Formula: a = n1 - n2 / n3 - n4;
        Double a = (n1 - n2) / (n3 - n4);
        System.out.println("RESULTADO a=" + a);

        return a;
    }

    private static Double somaColunaAoQuadrado(Double[] coluna) {
        double out = 0.0;
        for (Double num : coluna) {
            out += Math.pow(num, 2);
        }
        System.out.println("somaColunaAoQuadrado: " + out);
        return out;
    }

    private static Double somaColuna(Double[] coluna) {
        Double out = 0.0;
        for (Double num : coluna) {
            out += num;
        }
        System.out.println("somaColuna: " + out);
        return out;
    }

    private static Double somaColunasMultiplicando(Double[] colunaX, Double[] colunay) {
        Double out = 0.0;
        for (int n = 0; n < colunaX.length; n++) {
            out += (colunaX[n] * colunay[n]);
        }
        System.out.println("somaColunasMultiplicando: " + out);
        return out;
    }

    /**
     * Step 2
     */
    private static Double encontrarValorB(Double[] colunaX, Double[] colunay) {

        // N1 =  n * Σ(x*y)
        Double n1 = colunaX.length * somaColunasMultiplicando(colunaX, colunay);
        System.out.println("Linha 1: " + n1);

        // N2 = Σx * Σy
        Double n2 = somaColuna(colunaX) * somaColuna(colunay);
        System.out.println("Linha 2: " + n2);

        // N3 = n * Σx²
        Double n3 = colunaX.length * somaColunaAoQuadrado(colunaX);
        System.out.println("Linha 3: " + n3);

        // N4 = (Σx)²
        Double n4 = Math.pow(somaColuna(colunaX), 2);
        System.out.println("Linha 4: " + n4);

        //Formula: a = n1 - n2 / n3 - n4;
        Double b = (n1 - n2) / (n3 - n4);
        System.out.println("RESULTADO b=" + b);

        return b;
    }

}
