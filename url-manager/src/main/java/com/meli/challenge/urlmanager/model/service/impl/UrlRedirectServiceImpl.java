package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlAccessedEvent;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDataRepository;
import com.meli.challenge.urlmanager.model.exception.ServiceException;
import com.meli.challenge.urlmanager.model.service.UrlRedirectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.meli.challenge.urlmanager.model.constants.ErrorCode.*;

@Slf4j
@Service
public class UrlRedirectServiceImpl implements UrlRedirectService {
    private final UrlDataRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public UrlRedirectServiceImpl(ApplicationEventPublisher eventPublisher, UrlDataRepository repository) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @Override
    public Mono<UrlResponse> getOriginalUrl(String shortUrl) {
        log.info("shortUrl: {}", shortUrl);
        return repository.findByShortUrl(shortUrl)
                .switchIfEmpty(Mono.error(new ServiceException(URL_NOT_FOUND.getMessage(), URL_NOT_FOUND.getCode())))
                .flatMap(urlData -> {
                    if (!urlData.isEnabled()) {
                        return Mono.error(new ServiceException(URL_DISABLED.getMessage(), URL_DISABLED.getCode()));
                    }
                    eventPublisher.publishEvent(new UrlAccessedEvent(urlData));
                    return Mono.just(new UrlResponse(urlData.getOriginalUrl()));
                })
                .onErrorMap(e -> {
                    if (e instanceof ServiceException) {
                        return e;
                    }
                    return new ServiceException(UNKNOWN_ERROR.getMessage(), UNKNOWN_ERROR.getCode());
                });
    }
}