package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.TestConfig;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.service.UrlManagementService;
import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(UrlRedirectController.class)
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class UrlRedirectControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UrlRedirectService urlService;

    @Value("${endpointshort.url.redirect}")
    private String BASE_URL;

    @Test
    void redirectUrl_ShouldReturnFoundUrl_WithLocationHeader() {
        String shortUrlId = "short-id";
        String originalUrl = "http://example.com/original-url";
        UrlResponse urlResponse = new UrlResponse(originalUrl);

        Mockito.when(urlService.getOriginalUrl(shortUrlId))
                .thenReturn(Mono.just(urlResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(BASE_URL)
                        .queryParam("id", shortUrlId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().location(originalUrl)
                .expectBody(UrlResponse.class)
                .value(response -> {
                    assertEquals(originalUrl, response.getOriginalUrl());
                });

        Mockito.verify(urlService, Mockito.times(1)).getOriginalUrl(shortUrlId);
    }

    @Test
    void redirectUrl_ShouldReturnNotFound_WhenUrlNotExists() {
        String shortUrlId = "non-existent-id";

        Mockito.when(urlService.getOriginalUrl(shortUrlId))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(BASE_URL)
                        .queryParam("id", shortUrlId)
                        .build())
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(urlService, Mockito.times(1)).getOriginalUrl(shortUrlId);
    }
}
