package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDatalRepository;
import com.meli.challenge.urlmanager.model.service.UrlManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.meli.challenge.urlmanager.model.constants.Constants.MELI_SHORT_PATH;

@Slf4j
@Service
@AllArgsConstructor
public class UrlManagementServiceImpl implements UrlManagementService {
    private final UrlDatalRepository repository;


    public Mono<UrlData> createUrlData(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl)
                .switchIfEmpty(
                        Mono.defer(() -> createNewUrlData(originalUrl).flatMap(repository::save))
                );
    }

    private Mono<UrlData> createNewUrlData(String originalUrl) {
        log.info("creating data for: {}", originalUrl);
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
        return MELI_SHORT_PATH + System.currentTimeMillis();
    }
}
