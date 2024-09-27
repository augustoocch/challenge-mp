package com.meli.challenge.urlshortener.domain.rest.controller;

import com.meli.challenge.urlshortener.domain.rest.dto.UrlRequest;
import com.meli.challenge.urlshortener.model.entity.ShortenedUrl;
import com.meli.challenge.urlshortener.model.service.UrlManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/management/url")
public class UrlManagementController {
    private final UrlManagementService service;

    public UrlManagementController(UrlManagementService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<ShortenedUrl>> createShortUrl(
            @RequestBody UrlRequest request) {
        return service.createShortenedUrl(request.getUrl())
                .map(url -> ResponseEntity.ok(url));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ShortenedUrl>> getShortUrl(@PathVariable String id) {
        return service.getShortenedUrl(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<ShortenedUrl> getAllUrls() {
        return service.getAllUrls();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ShortenedUrl>> updateUrl(@PathVariable String id, @RequestBody UrlRequest request) {
        return service.updateUrl(id, request.getUrl())
                .map(url -> ResponseEntity.ok(url))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/enable/{id}")
    public Mono<ResponseEntity<Void>> toggleUrl(@PathVariable String id) {
        return service.toggleUrl(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PatchMapping("/disable/{id}")
    public Mono<ResponseEntity<Void>> disableUrl(@PathVariable String id) {
        return service.toggleUrl(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
