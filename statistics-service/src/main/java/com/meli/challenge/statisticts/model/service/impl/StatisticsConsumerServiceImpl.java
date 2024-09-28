package com.meli.challenge.statisticts.model.service.impl;

import com.meli.challenge.statisticts.domain.rest.dto.UrlDataDto;
import com.meli.challenge.statisticts.model.entity.repository.StatisticRepository;
import com.meli.challenge.statisticts.model.service.StatisticsConsumerService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsConsumerServiceImpl implements StatisticsConsumerService {
    private final StatisticRepository statisticRepository;


    @KafkaListener(topics = "url-access-events", groupId = "statistics-service-group")
    public void listen(UrlDataDto event) {
        //statisticRepository.save(event);
    }
}