package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.domain.rest.controller.documentation.UrlRedirectControllerInfo;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("${endpointshort.url.redirect}")
@AllArgsConstructor
@Slf4j
public class UrlRedirectController implements UrlRedirectControllerInfo {
    private final UrlRedirectService urlService;

    @GetMapping
    public Mono<ResponseEntity<UrlResponse>> redirectUrl(
            @RequestParam String id) {
        return urlService.getOriginalUrl(id)
                .map(url -> ResponseEntity.status(HttpStatus.OK)
                        .location(URI.create(url.getOriginalUrl()))
                        .body(new UrlResponse(url.getOriginalUrl())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}