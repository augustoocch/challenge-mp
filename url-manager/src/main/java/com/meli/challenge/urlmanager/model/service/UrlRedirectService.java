package com.meli.challenge.urlmanager.model.service;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import reactor.core.publisher.Mono;

public interface UrlRedirectService {
    Mono<UrlResponse> getOriginalUrl(String shortUrl);
    //Mono<Object> sendAccessEventReactive(UrlData event);
}
