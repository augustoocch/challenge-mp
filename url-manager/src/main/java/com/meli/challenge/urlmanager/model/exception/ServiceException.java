package com.meli.challenge.urlmanager.model.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private final int code;

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }
}
