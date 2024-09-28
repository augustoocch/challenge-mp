package com.meli.challenge.urlshortener.model.entity.repository;

import com.meli.challenge.urlshortener.model.entity.UrlData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UrlDatalRepository extends ReactiveMongoRepository<UrlData, String> {
    Mono<UrlData> findByShortUrl(String shortUrl);
    Mono<UrlData> findByOriginalUrl(String originalUrl);
}