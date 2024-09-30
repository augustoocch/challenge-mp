package com.meli.challenge.urlmanager.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import static com.meli.challenge.urlmanager.model.constants.Constants.FORMAT_DATE_DD_MM_YYYY_SS;

@Configuration
public class Config {

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern(FORMAT_DATE_DD_MM_YYYY_SS);
    }
}
