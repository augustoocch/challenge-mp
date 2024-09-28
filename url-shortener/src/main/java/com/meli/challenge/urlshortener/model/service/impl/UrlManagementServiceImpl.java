package com.meli.challenge.urlshortener.model.service.impl;

import com.meli.challenge.urlshortener.model.entity.UrlData;
import com.meli.challenge.urlshortener.model.entity.repository.UrlDatalRepository;
import com.meli.challenge.urlshortener.model.service.UrlManagementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UrlManagementServiceImpl implements UrlManagementService {
        private final UrlDatalRepository repository;

        public UrlManagementServiceImpl(UrlDatalRepository repository) {
            this.repository = repository;
        }

    public Mono<UrlData> createUrlData(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl)
                .switchIfEmpty(
                        Mono.defer(() -> createNewUrlData(originalUrl).flatMap(repository::save))
                );
    }

    private Mono<UrlData> createNewUrlData(String originalUrl) {
        UrlData url = new UrlData();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(generateShortUrl());
        url.setAccessCount(0);
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        url.setEnabled(true);
        return Mono.just(url);
    }

        public Mono<UrlData> getUrlData(String shortUrl) {
            return repository.findByShortUrl(shortUrl)
                    .doOnSuccess(url -> url.setAccessCount(url.getAccessCount() + 1))
                    .flatMap(repository::save);
        }

        public Flux<UrlData> getAllUrls() {
            return repository.findAll();
        }

        public Mono<UrlData> updateUrl(String id, String newUrl) {
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
