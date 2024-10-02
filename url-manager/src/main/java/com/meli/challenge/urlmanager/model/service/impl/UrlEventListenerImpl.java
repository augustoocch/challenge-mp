package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlAccessedEvent;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlDataDto;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.service.UrlEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.meli.challenge.urlmanager.model.constants.Constants.TOPIC_EVENTS;

@Slf4j
@Service
public class UrlEventListenerImpl implements UrlEventListener {
    private final KafkaTemplate<String, UrlDataDto> kafkaTemplate;

    public UrlEventListenerImpl(KafkaTemplate<String, UrlDataDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @EventListener
    public void handleUrlAccessed(UrlAccessedEvent event) {
        log.info("Sending event to Kafka: {}", event);
        UrlData urlData = event.getUrlData();
        UrlDataDto urlDataDto = new UrlDataDto(UUID(), urlData.getShortUrl(), urlData.getOriginalUrl(), urlData.getCreatedAt());
        kafkaTemplate.send(TOPIC_EVENTS, urlDataDto);
        log.info("Event sent to Kafka: {}", urlDataDto);
    }

    private String UUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
