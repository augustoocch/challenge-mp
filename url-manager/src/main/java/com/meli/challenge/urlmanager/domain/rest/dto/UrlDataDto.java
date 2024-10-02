package com.meli.challenge.urlmanager.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlDataDto {
    private String UUID;
    private String shortUrl;
    private String originalUrl;
    private LocalDateTime createdAt;
}