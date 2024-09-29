package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDataRepository;
import com.meli.challenge.urlmanager.model.exception.ServiceException;
import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.meli.challenge.urlmanager.model.constants.Constants.TOPIC_EVENTS;
import static com.meli.challenge.urlmanager.model.constants.ErrorCode.URL_DISABLED;
import static com.meli.challenge.urlmanager.model.constants.ErrorCode.URL_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class UrlRedirectServiceImpl implements UrlRedirectService {
    private final KafkaTemplate<String, UrlData> kafkaTemplate;
    private final UrlDataRepository repository;


    @Override
    public Mono<UrlResponse> getOriginalUrl(String shortUrl) {
        log.info("shortUrl: {}", shortUrl);
        return repository.findByShortUrl(shortUrl)
                .filter(UrlData::isEnabled)
                .doOnSuccess(this::sendAccessEvent)
                .map(i -> new UrlResponse(i.getOriginalUrl()))
                .switchIfEmpty(Mono.error(new ServiceException(URL_NOT_FOUND.getMessage(), URL_NOT_FOUND.getCode())))
                .onErrorMap(e -> {
                    return new ServiceException(URL_DISABLED.getMessage(), URL_DISABLED.getCode());
                });
    }

    @Override
    public void sendAccessEvent(UrlData event) {
        try {
            log.info("Sending event: {}", event);
            kafkaTemplate.send(TOPIC_EVENTS, event);
        } catch (Exception e) {
            log.info("Error sending event: {}", e.getMessage());
        }
    }

}
