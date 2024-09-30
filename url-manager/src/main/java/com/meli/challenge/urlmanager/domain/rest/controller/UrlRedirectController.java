package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class UrlRedirectController {
    private final UrlRedirectService urlService;


    @Operation(summary = "Redirigir a una URL original",
            description = "Obtiene la URL original a partir de su ID y redirige a esa URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Redirección exitosa a la URL original"),
            @ApiResponse(responseCode = "404", description = "URL no encontrada")
    })
    @GetMapping
    public Mono<ResponseEntity<UrlResponse>> redirectUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id) {
        return urlService.getOriginalUrl(id)
                .map(url -> ResponseEntity.status(HttpStatus.OK)
                        .location(URI.create(url.getOriginalUrl()))
                        .body(new UrlResponse(url.getOriginalUrl())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}