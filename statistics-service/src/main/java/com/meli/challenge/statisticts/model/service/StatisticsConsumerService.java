package com.meli.challenge.statisticts.model.service;

import com.meli.challenge.statisticts.model.dto.UrlDataDto;
import org.springframework.kafka.support.Acknowledgment;

public interface StatisticsConsumerService {
    void listen(UrlDataDto urlDataDto, Acknowledgment acknowledgment);
}
