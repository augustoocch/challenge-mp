package com.meli.challenge.urlmanager.model.entity.repository;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UrlDataRepository extends ReactiveMongoRepository<UrlData, String> {
    Mono<UrlData> findByShortUrl(String shortUrl);
    Mono<UrlData> findByOriginalUrl(String originalUrl);
}