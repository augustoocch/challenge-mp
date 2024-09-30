package com.meli.challenge.statisticts.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Statistic implements Serializable {
    @Serial
    private static final long serialVersionUID = 1784213L;
    @JsonProperty("shortUrl")
    private String shortUrl;
    @JsonProperty("originalUrl")
    private String originalUrl;
    @JsonProperty("accessCount")
    private int accessCount;
    @JsonProperty("lastAccessedAt")
    private String lastAccessedAt;

    public void incrementAccessCount() {
        this.accessCount++;
        this.lastAccessedAt = LocalDateTime.now().toString();
    }
}