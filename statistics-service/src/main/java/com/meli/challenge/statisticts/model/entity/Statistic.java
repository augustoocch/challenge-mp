package com.meli.challenge.statisticts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Statistic {
    private String shortUrl;
    private String originalUrl;
    private int accessCount;
    private LocalDateTime lastAccessedAt;

    public void incrementAccessCount() {
        this.accessCount++;
        this.lastAccessedAt = LocalDateTime.now();
    }
}