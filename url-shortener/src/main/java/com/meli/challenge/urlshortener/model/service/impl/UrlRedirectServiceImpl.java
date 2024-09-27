package com.meli.challenge.urlshortener.model.service.impl;

import com.meli.challenge.urlshortener.model.entity.ShortenedUrl;
import com.meli.challenge.urlshortener.model.entity.repository.ShortenedUrlRepository;
import com.meli.challenge.urlshortener.model.service.UrlRedirectService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.meli.challenge.urlshortener.model.constants.Constants.TOPIC_EVENTS;

@Service
public class UrlRedirectServiceImpl implements UrlRedirectService {
    private final KafkaTemplate<String, ShortenedUrl> kafkaTemplate;
    private final ShortenedUrlRepository repository;

    public UrlRedirectServiceImpl(KafkaTemplate<String, ShortenedUrl> kafkaTemplate,
                                  ShortenedUrlRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
    }

    @Override
    public Mono<ShortenedUrl> getOriginalUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .doOnSuccess(url -> url.setAccessCount(url.getAccessCount() + 1))
                .doOnSuccess(this::sendAccessEvent);
    }

    @Override
    public void sendAccessEvent(ShortenedUrl event) {
        kafkaTemplate.send(TOPIC_EVENTS, event);
    }

}
