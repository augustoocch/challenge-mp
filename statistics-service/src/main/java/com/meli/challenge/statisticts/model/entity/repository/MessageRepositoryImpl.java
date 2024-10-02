package com.meli.challenge.statisticts.model.entity.repository;

import com.meli.challenge.statisticts.model.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MessageRepositoryImpl implements MessageRepository{
    @Qualifier(value = "redisTemplateMessage")
    private final RedisTemplate<String, Message> redisTemplate;

    public MessageRepositoryImpl(RedisTemplate<String, Message> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<Message> findByUUID(String UUID) {
        Message message = redisTemplate.opsForValue().get(UUID);
        return Optional.ofNullable(message);
    }

    @Override
    public void save(Message message) {
        redisTemplate.opsForValue().set(message.getUUID(), message);
    }
}
