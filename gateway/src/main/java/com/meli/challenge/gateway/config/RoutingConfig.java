package com.meli.challenge.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RoutingConfig {

    @Value("${url-management-service.url}")
    private String URL_MANAGEMENT_SERVICE_URL;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("url-management-service", r -> r.path("/url/management/**")
                        .and().method("POST", "PUT", "PATCH", "DELETE", "GET")
                        .uri(URL_MANAGEMENT_SERVICE_URL))
                .route("url-shortener-service", r -> r.path("/api/short/url/**")
                        .and().method("GET")
                        .uri(URL_MANAGEMENT_SERVICE_URL))
                .build();
    }
}