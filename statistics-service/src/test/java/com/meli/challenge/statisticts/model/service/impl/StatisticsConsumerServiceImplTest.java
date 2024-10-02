package com.meli.challenge.statisticts.model.service.impl;

import com.meli.challenge.statisticts.model.dto.UrlDataDto;
import com.meli.challenge.statisticts.model.entity.Message;
import com.meli.challenge.statisticts.model.entity.Statistic;
import com.meli.challenge.statisticts.model.entity.repository.MessageRepository;
import com.meli.challenge.statisticts.model.entity.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class StatisticsConsumerServiceImplTest {
    @Mock
    private StatisticRepository statisticRepository;
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private StatisticsConsumerServiceImpl statisticsConsumerService;
    @Mock
    Acknowledgment acknowledgment;

    private UrlDataDto urlDataDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        urlDataDto = new UrlDataDto();
        urlDataDto.setShortUrl("short-url");
        urlDataDto.setOriginalUrl("http://original-url.com");
    }

    @Test
    void listen_ShouldProcessMessageAndUpdateStatistics_WhenMessageIsNotDuplicated() {
        Statistic statistic = new Statistic("short-url", "http://original-url.com", 5, LocalDateTime.now().toString());
        when(messageRepository.findByUUID(urlDataDto.getUUID())).thenReturn(Optional.empty());
        when(statisticRepository.findByShortUrl(urlDataDto.getShortUrl())).thenReturn(Optional.of(statistic));
        statisticsConsumerService.listen(urlDataDto, acknowledgment);

        verify(messageRepository, times(1)).save(any(Message.class));
        verify(statisticRepository, times(1)).findByShortUrl(urlDataDto.getShortUrl());
        verify(statisticRepository, times(1)).save(statistic);
        verify(acknowledgment, never()).acknowledge();
    }

    @Test
    void listen_ShouldNotUpdateStatistics_WhenMessageIsDuplicated() {
        Message message = new Message(urlDataDto.getUUID());
        when(messageRepository.findByUUID(urlDataDto.getUUID())).thenReturn(Optional.of(message));
        statisticsConsumerService.listen(urlDataDto, acknowledgment);

        verify(statisticRepository, never()).findByShortUrl(anyString());
        verify(statisticRepository, never()).save(any(Statistic.class));
        verify(acknowledgment, times(1)).acknowledge();
    }

    @Test
    void listen_ShouldCreateNewStatisticAndSave_WhenStatisticDoesNotExist() {
        when(messageRepository.findByUUID(urlDataDto.getUUID())).thenReturn(Optional.empty());
        when(statisticRepository.findByShortUrl(urlDataDto.getShortUrl())).thenReturn(Optional.empty());
        statisticsConsumerService.listen(urlDataDto, acknowledgment);

        verify(statisticRepository, times(1)).save(any(Statistic.class));
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(acknowledgment, never()).acknowledge();
    }

    @Test
    void listen_ShouldHandleException_WhenAnErrorOccurs() {
        when(messageRepository.findByUUID(urlDataDto.getUUID())).thenThrow(new RuntimeException("Database error"));
        statisticsConsumerService.listen(urlDataDto, acknowledgment);

        verify(acknowledgment, never()).acknowledge();
    }
}