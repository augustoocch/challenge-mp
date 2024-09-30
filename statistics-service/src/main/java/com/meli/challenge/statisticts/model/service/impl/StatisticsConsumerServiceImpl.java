package com.meli.challenge.statisticts.model.service.impl;

import com.meli.challenge.statisticts.model.dto.UrlDataDto;
import com.meli.challenge.statisticts.model.entity.Statistic;
import com.meli.challenge.statisticts.model.entity.repository.StatisticRepository;
import com.meli.challenge.statisticts.model.service.StatisticsConsumerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static com.meli.challenge.statisticts.model.constants.Constants.STATISTICS_GROUP;
import static com.meli.challenge.statisticts.model.constants.Constants.TOPIC_EVENTS;

@Service
@AllArgsConstructor
@Slf4j
public class StatisticsConsumerServiceImpl implements StatisticsConsumerService {
    StatisticRepository statisticRepository;

    @KafkaListener(topics = TOPIC_EVENTS, groupId = STATISTICS_GROUP)
    public void listen(UrlDataDto urlDataDto) {
        try {
            log.info("Mensaje recibido a las {}: {} ", ZonedDateTime.now(), urlDataDto);
            String shortUrl = urlDataDto.getShortUrl();

            Statistic statistic = statisticRepository.findByShortUrl(shortUrl)
                    .orElse(new Statistic(shortUrl, urlDataDto.getOriginalUrl(), 0, LocalDateTime.now().toString()));

            statistic.incrementAccessCount();
            log.info("Estadísticas actualizadas: {}", statistic.toString());
            statisticRepository.save(statistic);
        } catch (Exception e) {
            log.error("Error al procesar el mensaje: {}", e.getMessage());
        }
    }
}