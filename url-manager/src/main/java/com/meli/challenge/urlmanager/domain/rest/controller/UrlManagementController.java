package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlRequest;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.service.UrlManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${endpoint.url.management}")
@AllArgsConstructor
public class UrlManagementController {
    private final UrlManagementService service;

    @PostMapping
    public Mono<ResponseEntity<UrlData>> createShortUrl(
            @RequestBody UrlRequest request) {
        return service.createUrlData(request.getUrl())
                .map(url -> ResponseEntity.ok(url));
    }

    @GetMapping()
    public Mono<ResponseEntity<UrlData>> getShortUrl(@RequestParam String id) {
        return service.getUrlData(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UrlData>> updateUrl(@PathVariable String id, @RequestBody UrlRequest request) {
        return service.updateUrl(id, request.getUrl())
                .map(url -> ResponseEntity.ok(url))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("${endpoint.url.management.enable}")
    public Mono<ResponseEntity<Void>> toggleUrl(@RequestParam String id) {
        return service.enableUrl(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PatchMapping("${endpoint.url.management.disable}")
    public Mono<ResponseEntity<Void>> disableUrl(@RequestParam String id) {
        return service.disableUrl(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
