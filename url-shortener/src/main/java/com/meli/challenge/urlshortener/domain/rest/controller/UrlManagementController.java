package com.meli.challenge.urlshortener.domain.rest.controller;

import com.meli.challenge.urlshortener.domain.rest.dto.UrlRequest;
import com.meli.challenge.urlshortener.model.entity.UrlData;
import com.meli.challenge.urlshortener.model.service.UrlManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/url/management")
public class UrlManagementController {
    private final UrlManagementService service;

    public UrlManagementController(UrlManagementService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<UrlData>> createShortUrl(
            @RequestBody UrlRequest request) {
        return service.createUrlData(request.getUrl())
                .map(url -> ResponseEntity.ok(url));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UrlData>> getShortUrl(@PathVariable String id) {
        return service.getUrlData(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<UrlData> getAllUrls() {
        return service.getAllUrls();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UrlData>> updateUrl(@PathVariable String id, @RequestBody UrlRequest request) {
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
