package com.meli.challenge.urlmanager.model.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    URL_NOT_FOUND("URL not found", 404),
    URL_DISABLED("URL disabled", 403),
    URL_ALREADY_EXISTS("URL already exists", 409),
    INVALID_URL("Invalid URL", 400),
    UNKNOWN_ERROR("Unknown error", 500);

    private final String message;
    private final int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
