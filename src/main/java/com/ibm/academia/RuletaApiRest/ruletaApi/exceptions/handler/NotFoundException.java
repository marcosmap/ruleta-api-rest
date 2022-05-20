package com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
    private static final long serialVersionUID = 5730514173024046349L;
}
