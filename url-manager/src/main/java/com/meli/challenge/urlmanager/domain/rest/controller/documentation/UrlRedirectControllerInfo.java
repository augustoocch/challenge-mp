package com.meli.challenge.urlmanager.domain.rest.controller.documentation;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface UrlRedirectControllerInfo {

    @Operation(summary = "Redirigir a una URL original",
            description = "Obtiene la URL original a partir de su ID y redirige a esa URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Redirección exitosa a la URL original"),
            @ApiResponse(responseCode = "404", description = "URL no encontrada")
    })
    @GetMapping
    Mono<ResponseEntity<UrlResponse>> redirectUrl(
            @Parameter(description = "ID de la URL corta")
            @RequestParam String id);
}
