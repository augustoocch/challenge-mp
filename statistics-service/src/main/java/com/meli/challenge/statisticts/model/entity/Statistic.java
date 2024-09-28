package com.meli.challenge.statisticts.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "url-statistics")
@Getter
@Setter
@AllArgsConstructor
public class Statistic {
    @Id
    private String eventId;
    @Field(type = FieldType.Keyword)
    private String shortUrl;
    @Field(type = FieldType.Keyword)
    private String originalUrl;
    @Field(type = FieldType.Keyword)
    private String ipAddress;
    @Field(type = FieldType.Text)
    private String userAgent;
    @Field(type = FieldType.Text)
    private String referer;
    @Field(type = FieldType.Keyword)
    private String country;
    @Field(type = FieldType.Keyword)
    private String city;
    @Field(type = FieldType.Keyword)
    private String urlStatus;
    @Field(type = FieldType.Integer)
    private int httpResponseCode;
    @Field(type = FieldType.Long)
    private long responseTime;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Keyword)
    private String eventType;
    @Field(type = FieldType.Date)
    private Instant timestamp;
}