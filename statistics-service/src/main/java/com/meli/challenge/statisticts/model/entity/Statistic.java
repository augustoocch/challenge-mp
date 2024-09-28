package com.meli.challenge.statisticts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class Statistic {
    private String eventId;
    private String shortUrl;
    private String originalUrl;
    private String ipAddress;
    private String userAgent;
    private String referer;
    private String country;
    private String city;
    private String urlStatus;
    private int httpResponseCode;
    private long responseTime;
    private String userId;
    private String eventType;
    private Instant timestamp;
}