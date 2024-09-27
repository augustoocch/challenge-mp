package com.meli.challenge.urlshortener.model.service;

import com.meli.challenge.urlshortener.model.entity.ShortenedUrl;
import reactor.core.publisher.Mono;

public interface UrlRedirectService {
    Mono<ShortenedUrl> getOriginalUrl(String shortUrl);
    void sendAccessEvent(ShortenedUrl event);
}
