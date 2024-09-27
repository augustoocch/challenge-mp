package com.meli.challenge.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class GatewayConfig {

    @Value("${url-shortener-service.url}")
    private String URL_SHORTENER_SERVICE_URL;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        log.info("URL_SHORTENER_SERVICE_URI: {}", URL_SHORTENER_SERVICE_URL);
        return builder.routes()
                .route("url-shortener-service", r -> r.path("/management/url")
                        .and().method("POST",  "PUT", "DELETE")
                        .and().host("localhost*")
                        .uri(URL_SHORTENER_SERVICE_URL))
                .build();
    }
}
