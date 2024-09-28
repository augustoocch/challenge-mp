package com.meli.challenge.statisticts.model.service;

import com.meli.challenge.statisticts.domain.rest.dto.UrlDataDto;

public interface StatisticsConsumerService {
    void listen(UrlDataDto urlDataDto);
}
