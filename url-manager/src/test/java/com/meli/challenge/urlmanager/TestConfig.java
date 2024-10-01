package com.meli.challenge.urlmanager;

import com.meli.challenge.urlmanager.domain.rest.controller.advice.ControllerHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeFormatter;

@TestConfiguration
public class TestConfig {
    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
