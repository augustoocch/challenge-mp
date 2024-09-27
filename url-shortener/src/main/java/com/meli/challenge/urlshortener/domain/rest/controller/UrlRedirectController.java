package com.meli.challenge.urlshortener.domain.rest.controller;

import com.meli.challenge.urlshortener.model.service.UrlRedirectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/shortener")
public class UrlRedirectController {
    private final UrlRedirectService urlService;

    public UrlRedirectController(UrlRedirectService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrl}")
    public Mono<ResponseEntity<Object>> redirectUrl(@PathVariable String shortUrl) {
        return urlService.getOriginalUrl(shortUrl)
                .map(shortenedUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(shortenedUrl.getOriginalUrl()))
                        .build()
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}