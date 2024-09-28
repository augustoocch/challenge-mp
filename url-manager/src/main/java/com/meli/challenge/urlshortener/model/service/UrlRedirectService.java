package com.meli.challenge.urlshortener.model.service;

import com.meli.challenge.urlshortener.model.entity.UrlData;
import reactor.core.publisher.Mono;

public interface UrlRedirectService {
    Mono<UrlData> getOriginalUrl(String shortUrl);
    void sendAccessEvent(UrlData event);
}
