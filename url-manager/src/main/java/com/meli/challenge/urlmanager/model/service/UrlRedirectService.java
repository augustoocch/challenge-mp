package com.meli.challenge.urlmanager.model.service;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import reactor.core.publisher.Mono;

public interface UrlRedirectService {
    Mono<UrlData> getOriginalUrl(String shortUrl);
    void sendAccessEvent(UrlData event);
}
