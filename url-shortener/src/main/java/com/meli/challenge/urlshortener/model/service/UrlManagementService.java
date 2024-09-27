package com.meli.challenge.urlshortener.model.service;

import com.meli.challenge.urlshortener.model.entity.ShortenedUrl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UrlManagementService {
    Mono<ShortenedUrl> createShortenedUrl(String originalUrl);
    Mono<ShortenedUrl> getShortenedUrl(String shortUrl);
    Flux<ShortenedUrl> getAllUrls();
    Mono<ShortenedUrl> updateUrl(String id, String newUrl);
    Mono<Void> toggleUrl(String id);
}
