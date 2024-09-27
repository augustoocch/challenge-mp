package com.meli.challenge.urlshortener.model.service.impl;

import com.meli.challenge.urlshortener.model.entity.ShortenedUrl;
import com.meli.challenge.urlshortener.model.entity.repository.ShortenedUrlRepository;
import com.meli.challenge.urlshortener.model.service.UrlManagementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UrlManagementServiceImpl implements UrlManagementService {
        private final ShortenedUrlRepository repository;

        public UrlManagementServiceImpl(ShortenedUrlRepository repository) {
            this.repository = repository;
        }

    public Mono<ShortenedUrl> createShortenedUrl(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl)
                .switchIfEmpty(
                        Mono.defer(() -> createNewShortenedUrl(originalUrl).flatMap(repository::save))
                );
    }

    private Mono<ShortenedUrl> createNewShortenedUrl(String originalUrl) {
        ShortenedUrl url = new ShortenedUrl();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(generateShortUrl());
        url.setAccessCount(0);
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        url.setEnabled(true);
        return Mono.just(url);
    }

        public Mono<ShortenedUrl> getShortenedUrl(String shortUrl) {
            return repository.findByShortUrl(shortUrl)
                    .doOnSuccess(url -> url.setAccessCount(url.getAccessCount() + 1))
                    .flatMap(repository::save);
        }

        public Flux<ShortenedUrl> getAllUrls() {
            return repository.findAll();
        }

        public Mono<ShortenedUrl> updateUrl(String id, String newUrl) {
            return repository.findById(id)
                    .flatMap(url -> {
                        url.setOriginalUrl(newUrl);
                        url.setUpdatedAt(LocalDateTime.now());
                        return repository.save(url);
                    });
        }

        public Mono<Void> toggleUrl(String id) {
            return repository.findById(id)
                    .flatMap(url -> {
                        url.setEnabled(!url.isEnabled());
                        return repository.save(url);
                    }).then();
        }

        private String generateShortUrl() {
            return "short.ly/" + System.currentTimeMillis();
        }
    }
