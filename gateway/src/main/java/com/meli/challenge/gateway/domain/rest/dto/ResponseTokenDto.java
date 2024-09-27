package com.meli.challenge.gateway.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ResponseTokenDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String token;

}
