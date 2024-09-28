package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/short/url")
@AllArgsConstructor
@Slf4j
public class UrlRedirectController {
    private final UrlRedirectService urlService;


    @GetMapping()
    public Mono<ResponseEntity<Object>> redirectUrl(@RequestParam String id) {
        return urlService.getOriginalUrl(id)
                .map(shortenedUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(shortenedUrl.getOriginalUrl()))
                        .build()
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}