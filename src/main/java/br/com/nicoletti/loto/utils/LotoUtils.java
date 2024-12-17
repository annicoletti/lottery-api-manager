package br.com.nicoletti.loto.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import br.com.nicoletti.loto.exceptions.RestServiceException;

public class LotoUtils {

    public static boolean isNullOrEmpty(Object object) {

        if (object == null) {
            return true;
        }

        if (object instanceof String value) {
            return value.isBlank();
        }

        return false;
    }


    public static Instant parseStringToInstant(String dataInicial) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date parse = simpleDateFormat.parse(dataInicial);
            return parse.toInstant();

        } catch (ParseException e) {
            throw new RestServiceException(e.getMessage());
        }
    }
}
