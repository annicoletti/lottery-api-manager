package br.com.nicoletti.loto.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TesteDivisaoList {

    public static void main(String[] args) {


        int grupo = 5;
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);

        List<List<Integer>> resultado = new ArrayList<>();
        for (int i = 0, j = grupo; i <= lista.size(); i += grupo, j += grupo) {
            System.out.println("i: " + i + " grupo: " + j);

            if (j <= lista.size()) {
                resultado.add(lista.subList(i, j));
            } else {
                resultado.add(lista.subList(i, lista.size()));
            }
        }
        System.out.println(resultado);
    }

}
