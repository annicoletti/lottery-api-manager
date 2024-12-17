package br.com.nicoletti.loto.exceptions;

public class RestServiceException extends RuntimeException {

    public RestServiceException(String message) {
        super(message);
    }

}
