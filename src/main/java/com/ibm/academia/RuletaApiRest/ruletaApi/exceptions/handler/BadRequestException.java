package com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler;

public class BadRequestException extends RuntimeException {
    public BadRequestException (String message) {
        super(message);
    }
    private static final long serialVersionUID = 944708742719958429L;
}
