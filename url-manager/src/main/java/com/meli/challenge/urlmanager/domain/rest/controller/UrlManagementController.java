package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlRequest;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.service.UrlManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crear una URL corta",
            description = "Crea una nueva URL corta a partir de una URL original.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public Mono<ResponseEntity<UrlData>> createShortUrl(
            @Parameter(description = "Objeto que contiene la URL original")
            @RequestBody UrlRequest request) {
        return service.createUrlData(request.getUrl())
                .map(url -> ResponseEntity.ok(url));
    }

    @Operation(summary = "Obtener una URL corta",
            description = "Obtiene los datos de una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta encontrada"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @GetMapping
    public Mono<ResponseEntity<UrlData>> getShortUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id) {
        return service.getUrlData(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar una URL corta",
            description = "Actualiza la URL original de una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UrlData>> updateUrl(
            @Parameter(description = "ID de la URL corta")
            @PathVariable String id,
            @Parameter(description = "Objeto que contiene la nueva URL original")
            @RequestBody UrlRequest request) {
        return service.updateUrl(id, request.getUrl())
                .map(url -> ResponseEntity.ok(url))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Habilitar una URL corta",
            description = "Habilita una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta habilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @PatchMapping("${endpoint.url.management.enable}")
    public Mono<ResponseEntity<Void>> toggleUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id) {
        return service.enableUrl(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Operation(summary = "Deshabilitar una URL corta",
            description = "Deshabilita una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta deshabilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @PatchMapping("${endpoint.url.management.disable}")
    public Mono<ResponseEntity<Void>> disableUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id) {
        return service.disableUrl(id)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
