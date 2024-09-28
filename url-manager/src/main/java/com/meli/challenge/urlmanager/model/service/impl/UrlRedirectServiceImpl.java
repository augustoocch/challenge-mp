package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDatalRepository;
import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.meli.challenge.urlmanager.model.constants.Constants.TOPIC_EVENTS;

@Slf4j
@Service
@AllArgsConstructor
public class UrlRedirectServiceImpl implements UrlRedirectService {
    private final KafkaTemplate<String, UrlData> kafkaTemplate;
    private final UrlDatalRepository repository;


    @Override
    public Mono<UrlData> getOriginalUrl(String shortUrl) {
        log.info("shortUrl: {}", shortUrl);
        return repository.findByShortUrl(shortUrl)
                .doOnSuccess(url -> url.setAccessCount(url.getAccessCount() + 1))
                .doOnSuccess(this::sendAccessEvent);
    }

    @Override
    public void sendAccessEvent(UrlData event) {
        try {
            log.info("Sending event: {}", event);
            kafkaTemplate.send(TOPIC_EVENTS, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
