package com.meli.challenge.statisticts.model.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.challenge.statisticts.domain.rest.dto.UrlDataDto;
import com.meli.challenge.statisticts.model.service.StatisticsConsumerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static com.meli.challenge.statisticts.model.constants.Constants.TOPIC_EVENTS;

@Service
@AllArgsConstructor
@Slf4j
public class StatisticsConsumerServiceImpl implements StatisticsConsumerService {


    @KafkaListener(topics = TOPIC_EVENTS, groupId = "statistics-service-group")
    public void listen(UrlDataDto urlDataDto) {
        try{
            log.info("Mensaje recibido a las {}: {}    ", ZonedDateTime.now(),urlDataDto);
        } catch (Exception e) {
            log.error("Error al procesar el mensaje: {}", e.getMessage());
        }

    }
}