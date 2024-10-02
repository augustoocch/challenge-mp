package com.meli.challenge.urlmanager.model.service.impl;
import static com.meli.challenge.urlmanager.model.constants.Constants.TOPIC_EVENTS;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlAccessedEvent;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlDataDto;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UrlEventListenerImplTest {
    private KafkaTemplate<String, UrlDataDto> kafkaTemplate;
    private UrlEventListenerImpl urlEventListener;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        urlEventListener = new UrlEventListenerImpl(kafkaTemplate);
    }

    @Test
    void testHandleUrlAccessed_ShouldSendEventToKafka() {
        UrlData urlData = new UrlData();
        urlData.setShortUrl("short-url");
        urlData.setOriginalUrl("original-url");
        urlData.setCreatedAt(LocalDateTime.now());

        UrlAccessedEvent event = new UrlAccessedEvent(urlData);
        urlEventListener.handleUrlAccessed(event);

        ArgumentCaptor<UrlDataDto> urlDataDtoCaptor = ArgumentCaptor.forClass(UrlDataDto.class);
        Mockito.verify(kafkaTemplate).send(eq(TOPIC_EVENTS), urlDataDtoCaptor.capture());

        UrlDataDto capturedDto = urlDataDtoCaptor.getValue();
        assertNotNull(capturedDto.getUUID());
        assertEquals("short-url", capturedDto.getShortUrl());
        assertEquals("original-url", capturedDto.getOriginalUrl());
        assertEquals(urlData.getCreatedAt(), capturedDto.getCreatedAt());

        verify(kafkaTemplate, times(1)).send(anyString(), any(UrlDataDto.class));
    }
}