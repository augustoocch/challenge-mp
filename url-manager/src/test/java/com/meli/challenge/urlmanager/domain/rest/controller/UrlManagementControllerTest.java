package com.meli.challenge.urlmanager.domain.rest.controller;

import com.meli.challenge.urlmanager.TestConfig;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlRequest;
import com.meli.challenge.urlmanager.model.service.UrlManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@WebFluxTest(UrlManagementController.class)
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class UrlManagementControllerTest {
    @MockBean
    private UrlManagementService service;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${endpoint.url.management}")
    private String BASE_URL;

    @Value("${endpoint.url.management.enable}")
    private String ENABLE_URL;

    @Value("${endpoint.url.management.disable}")
    private String DISABLE_URL;


    @Test
    void createShortUrl_ShouldReturnCreatedUrlData() {
        String originalUrl = "http://ejemplo-prueba.com";
        UrlRequest request = new UrlRequest(originalUrl);
        UrlData urlData = new UrlData("id","meli.ly/312487", "http://ejemplo-prueba.com", true, LocalDateTime.now(),LocalDateTime.now());

        doReturn(Mono.just(urlData))
                .when(service).createUrlData(originalUrl);

        webTestClient.post().uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    Mono.just(request),
                    UrlRequest.class
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody(UrlData.class)
                .value(response -> {
                    assertEquals(urlData.getShortUrl(), response.getShortUrl());
                    assertEquals(urlData.getOriginalUrl(), response.getOriginalUrl());
                });

        Mockito.verify(service, Mockito.times(1)).createUrlData(originalUrl);
    }

    @Test
    void getShortUrl_ShouldReturnUrlData_WhenExists() {
        String shortUrlId = "meli.ly/124124";
        UrlData urlData = new UrlData("id",shortUrlId, "http://url-ejemplo1", true, null,null);

        Mockito.when(service.getUrlData(shortUrlId))
                .thenReturn(Mono.just(urlData));

        webTestClient.get().uri(BASE_URL + "?id=" + shortUrlId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UrlData.class)
                .value(response -> {
                    assertEquals(urlData.getShortUrl(), response.getShortUrl());
                    assertEquals(urlData.getOriginalUrl(), response.getOriginalUrl());
                });

        Mockito.verify(service, Mockito.times(1)).getUrlData(shortUrlId);
    }

    @Test
    void getShortUrl_ShouldReturnNotFound_WhenDoesNotExist() {
        String shortUrlId = "meli.ly/124124";

        Mockito.when(service.getUrlData(shortUrlId))
                .thenReturn(Mono.empty());

        webTestClient.get().uri(BASE_URL + "?id=" + shortUrlId)
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(service, Mockito.times(1)).getUrlData(shortUrlId);
    }

    @Test
    void updateUrl_ShouldReturnUpdatedUrlData_WhenSuccessful() {
        String shortUrlId = "meli.ly/124124";
        String newUrl = "http://new-url.com";
        UrlRequest request = new UrlRequest(newUrl);
        UrlData urlData = new UrlData("id",shortUrlId, "http://url-ejemplo1", true, null,null);

        Mockito.when(service.updateUrl(shortUrlId, newUrl))
                .thenReturn(Mono.just(urlData));

        webTestClient.put().uri(BASE_URL + "?id=" + shortUrlId)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UrlData.class)
                .value(response -> {
                    assertEquals(urlData.getShortUrl(), response.getShortUrl());
                    assertEquals(urlData.getOriginalUrl(), response.getOriginalUrl());
                });

        Mockito.verify(service, Mockito.times(1)).updateUrl(shortUrlId, newUrl);
    }

    @Test
    void updateUrl_ShouldReturnNotFound_WhenUrlDoesNotExist() {
        String shortUrlId = "meli.ly/124124";
        String newUrl = "http://new-url.com";
        UrlRequest request = new UrlRequest(newUrl);

        Mockito.when(service.updateUrl(shortUrlId, newUrl))
                .thenReturn(Mono.empty());

        webTestClient.put().uri(BASE_URL + "?id=" + shortUrlId)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(service, Mockito.times(1)).updateUrl(shortUrlId, newUrl);
    }

    @Test
    void enableUrl_ShouldReturnOk_WhenSuccessful() {
        String shortUrlId = "meli.ly/124124";

        Mockito.when(service.enableUrl(shortUrlId))
                .thenReturn(Mono.empty());

        webTestClient.patch().uri(BASE_URL + ENABLE_URL + "?id=" + shortUrlId)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(service, Mockito.times(1)).enableUrl(shortUrlId);
    }

    @Test
    void disableUrl_ShouldReturnOk_WhenSuccessful() {
        String shortUrlId = "meli.ly/124124";

        Mockito.when(service.disableUrl(shortUrlId))
                .thenReturn(Mono.empty());

        webTestClient.patch().uri(BASE_URL + DISABLE_URL + "?id=" + shortUrlId)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(service, Mockito.times(1)).disableUrl(shortUrlId);
    }
}

