package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlDataDto;
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
import static com.meli.challenge.urlmanager.model.constants.ErrorCode.*;

@Slf4j
@Service
@AllArgsConstructor
public class UrlRedirectServiceImpl implements UrlRedirectService {
    private final KafkaTemplate<String, UrlDataDto> kafkaTemplate;
    private final UrlDataRepository repository;


    @Override
    public Mono<UrlResponse> getOriginalUrl(String shortUrl) {
        log.info("shortUrl: {}", shortUrl);
        return repository.findByShortUrl(shortUrl)
                .switchIfEmpty(Mono.error(new ServiceException(URL_NOT_FOUND.getMessage(), URL_NOT_FOUND.getCode())))
                .flatMap(urlData -> {
                    if (!urlData.isEnabled()) {
                        return Mono.error(new ServiceException(URL_DISABLED.getMessage(), URL_DISABLED.getCode()));
                    }
                    return Mono.just(urlData);
                })
                .doOnSuccess(this::sendAccessEvent)
                .map(urlData -> new UrlResponse(urlData.getOriginalUrl()))
                .onErrorMap(e -> {
                    if (e instanceof ServiceException) {
                        return e;
                    }
                    return new ServiceException(UNKNOWN_ERROR.getMessage(), UNKNOWN_ERROR.getCode());
                });
    }

    @Override
    public void sendAccessEvent(UrlData event) {
        try {
            log.info("Sending event: {}", event);
            UrlDataDto urlDataDto = new UrlDataDto(UUID(), event.getShortUrl(), event.getOriginalUrl(), event.getCreatedAt());
            kafkaTemplate.send(TOPIC_EVENTS, urlDataDto);
        } catch (Exception e) {
            log.info("Error sending event: {}", e.getMessage());
        }
    }

    private String UUID() {
        return java.util.UUID.randomUUID().toString();
    }

}
