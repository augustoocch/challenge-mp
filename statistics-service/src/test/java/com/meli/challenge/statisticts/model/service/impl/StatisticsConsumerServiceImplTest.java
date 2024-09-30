package com.meli.challenge.statisticts.model.service.impl;

import com.meli.challenge.statisticts.model.dto.UrlDataDto;
import com.meli.challenge.statisticts.model.entity.Statistic;
import com.meli.challenge.statisticts.model.entity.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatisticsConsumerServiceImplTest  {
    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private StatisticsConsumerServiceImpl statisticsConsumerService;

    private UrlDataDto urlDataDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        urlDataDto = new UrlDataDto();
        urlDataDto.setShortUrl("short-url");
        urlDataDto.setOriginalUrl("http://original-url.com");
    }

    @Test
    void testListen_WhenStatisticExists() {
        Statistic existingStatistic = new Statistic("short-url", "http://original-url.com", 5, LocalDateTime.now().toString());
        when(statisticRepository.findByShortUrl(urlDataDto.getShortUrl()))
                .thenReturn(Optional.of(existingStatistic));
        statisticsConsumerService.listen(urlDataDto);
        assertEquals(6, existingStatistic.getAccessCount());
        verify(statisticRepository, times(1)).save(existingStatistic);
    }

    @Test
    void testListen_WhenStatisticDoesNotExist() {
        when(statisticRepository.findByShortUrl(urlDataDto.getShortUrl()))
                .thenReturn(Optional.empty());
        statisticsConsumerService.listen(urlDataDto);
        verify(statisticRepository, times(1)).save(any(Statistic.class));
    }

    @Test
    void testListen_ExceptionHandling() {
        when(statisticRepository.findByShortUrl(anyString())).thenThrow(new RuntimeException("Simulated error"));
        assertDoesNotThrow(() -> statisticsConsumerService.listen(urlDataDto));

    }
}