package com.meli.challenge.statisticts.domain.rest.dto;

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
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int accessCount;
    private LocalDateTime lastAccessedAt;
}