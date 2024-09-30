package com.meli.challenge.statisticts.model.dto;

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
    private String shortUrl;
    private String originalUrl;
    private LocalDateTime createdAt;
}