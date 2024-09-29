package com.meli.challenge.statisticts.model.entity.repository;

import com.meli.challenge.statisticts.model.entity.Statistic;

import java.util.Optional;

public interface StatisticRepository {
    Optional<Statistic> findByShortUrl(String shortUrl);
    void save(Statistic statistic);
}
