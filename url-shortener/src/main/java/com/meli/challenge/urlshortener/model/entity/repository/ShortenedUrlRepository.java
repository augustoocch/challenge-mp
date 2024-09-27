package com.meli.challenge.urlshortener.model.entity.repository;

import com.meli.challenge.urlshortener.model.entity.ShortenedUrl;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ShortenedUrlRepository extends ReactiveMongoRepository<ShortenedUrl, String> {
    Mono<ShortenedUrl> findByShortUrl(String shortUrl);
    Mono<ShortenedUrl> findByOriginalUrl(String originalUrl);
}