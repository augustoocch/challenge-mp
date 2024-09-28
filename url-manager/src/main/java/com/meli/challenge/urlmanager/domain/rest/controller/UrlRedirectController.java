package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("${endpointshort.url.redirect}")
@AllArgsConstructor
public class UrlRedirectController {
    private final UrlRedirectService urlService;

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