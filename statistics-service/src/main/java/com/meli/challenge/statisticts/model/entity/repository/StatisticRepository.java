package com.meli.challenge.statisticts.model.entity.repository;

import com.meli.challenge.statisticts.model.entity.Statistic;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository {
    Optional<Statistic> findByShortUrl(String shortUrl);
    void save(Statistic statistic);
}
