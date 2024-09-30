package com.meli.challenge.urlmanager.domain.rest.controller.documentation;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlRequest;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

public interface UrlManagementControllerInfo {

    @Operation(summary = "Crear una URL corta",
            description = "Crea una nueva URL corta a partir de una URL original.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    Mono<ResponseEntity<UrlData>> createShortUrl(
            @Parameter(description = "Objeto que contiene la URL original")
            @RequestBody UrlRequest request);

    @Operation(summary = "Obtener una URL corta",
            description = "Obtiene los datos de una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta encontrada"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @GetMapping
    Mono<ResponseEntity<UrlData>> getShortUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id);


    @Operation(summary = "Actualizar una URL corta",
            description = "Actualiza la URL original de una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @PutMapping("/{id}")
    Mono<ResponseEntity<UrlData>> updateUrl(
            @Parameter(description = "ID de la URL corta")
            @PathVariable String id,
            @Parameter(description = "Objeto que contiene la nueva URL original")
            @RequestBody UrlRequest request);


    @Operation(summary = "Habilitar una URL corta",
            description = "Habilita una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta habilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @PatchMapping("${endpoint.url.management.enable}")
    Mono<ResponseEntity<Void>> enable(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id);


    @Operation(summary = "Deshabilitar una URL corta",
            description = "Deshabilita una URL corta dada su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL corta deshabilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "URL corta no encontrada")
    })
    @PatchMapping("${endpoint.url.management.disable}")
    Mono<ResponseEntity<Void>> disableUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id);
}
