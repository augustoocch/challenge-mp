package com.meli.challenge.statisticts.model.entity.repository;

import com.meli.challenge.statisticts.model.entity.Message;
import com.meli.challenge.statisticts.model.entity.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;

public class MessageRepositoryImplTest {
    @Mock
    private RedisTemplate<String, Message> redisTemplate;
    @Mock
    private ValueOperations<String, Message> valueOperations;
    @InjectMocks
    private MessageRepositoryImpl messageRepository;
    private Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        message = new Message("uuid");
    }

    @Test
    void findByUUID_ShouldReturnMessage_WhenMessageExists() {
        when(valueOperations.get(message.getUUID())).thenReturn(message);
        Optional<Message> result = messageRepository.findByUUID(message.getUUID());

        assertTrue(result.isPresent());
        assertEquals(message.getUUID(), result.get().getUUID());
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(message.getUUID());
    }

    @Test
    void findByUUID_ShouldReturnEmptyOptional_WhenMessageDoesNotExist() {
        when(valueOperations.get(message.getUUID())).thenReturn(null);
        Optional<Message> result = messageRepository.findByUUID(message.getUUID());

        assertTrue(result.isEmpty());
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(message.getUUID());
    }

    @Test
    void save_ShouldSaveMessage_WhenValidMessageProvided() {
        messageRepository.save(message);

        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).set(message.getUUID(), message);
    }
}
