package com.meli.challenge.statisticts.model.entity.repository;


import com.meli.challenge.statisticts.model.entity.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticRepositoryImplTest {
    @Mock
    private RedisTemplate<String, Statistic> redisTemplate;
    @Mock
    private ValueOperations<String, Statistic> valueOperations;
    @InjectMocks
    private StatisticRepositoryImpl statisticRepositoryImpl;
    private Statistic statistic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        statistic = new Statistic("short-url", "http://original-url.com", 5, LocalDateTime.now().toString());
    }

    @Test
    void testFindByShortUrl_WhenExists() {
        when(valueOperations.get("short-url")).thenReturn(statistic);
        Optional<Statistic> result = statisticRepositoryImpl.findByShortUrl("short-url");

        assertTrue(result.isPresent());
        assertEquals(statistic, result.get());
    }

    @Test
    void testFindByShortUrl_WhenNotExists() {
        when(valueOperations.get("short-url")).thenReturn(null);
        Optional<Statistic> result = statisticRepositoryImpl.findByShortUrl("short-url");

        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        statisticRepositoryImpl.save(statistic);

        verify(valueOperations, times(1)).set(statistic.getShortUrl(), statistic, 24, TimeUnit.HOURS);
    }
}