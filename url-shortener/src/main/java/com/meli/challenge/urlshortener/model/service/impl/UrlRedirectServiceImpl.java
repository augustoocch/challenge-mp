package com.meli.challenge.urlshortener.model.service.impl;

import com.meli.challenge.urlshortener.model.entity.UrlData;
import com.meli.challenge.urlshortener.model.entity.repository.UrlDatalRepository;
import com.meli.challenge.urlshortener.model.service.UrlRedirectService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.meli.challenge.urlshortener.model.constants.Constants.TOPIC_EVENTS;

@Service
public class UrlRedirectServiceImpl implements UrlRedirectService {
    private final KafkaTemplate<String, UrlData> kafkaTemplate;
    private final UrlDatalRepository repository;

    public UrlRedirectServiceImpl(KafkaTemplate<String, UrlData> kafkaTemplate,
                                  UrlDatalRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
    }

    @Override
    public Mono<UrlData> getOriginalUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .doOnSuccess(url -> url.setAccessCount(url.getAccessCount() + 1))
                .doOnSuccess(this::sendAccessEvent);
    }

    @Override
    public void sendAccessEvent(UrlData event) {
        kafkaTemplate.send(TOPIC_EVENTS, event);
    }

}
