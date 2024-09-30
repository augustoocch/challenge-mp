package com.meli.challenge.statisticts.model.service;

import com.meli.challenge.statisticts.model.dto.UrlDataDto;

public interface StatisticsConsumerService {
    void listen(UrlDataDto urlDataDto);
}
