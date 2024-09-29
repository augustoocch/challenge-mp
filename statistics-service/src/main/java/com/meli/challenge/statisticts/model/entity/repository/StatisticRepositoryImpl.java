package com.meli.challenge.statisticts.model.entity.repository;

import com.meli.challenge.statisticts.model.entity.Statistic;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class StatisticRepositoryImpl implements StatisticRepository {
    private RedisTemplate<String, Statistic> redisTemplate;

    @Override
    public Optional<Statistic> findByShortUrl(String shortUrl) {
        Statistic statistic = redisTemplate.opsForValue().get(shortUrl);
        return Optional.ofNullable(statistic);
    }

    @Override
    public void save(Statistic statistic) {
        redisTemplate.opsForValue().set(statistic.getShortUrl(), statistic, 24, TimeUnit.HOURS);
    }
}
