package com.meli.challenge.urlmanager.model.exception;

public class ServiceException extends RuntimeException{

    private int code;

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
