package com.meli.challenge.urlmanager.model.service.impl;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlAccessedEvent;
import com.meli.challenge.urlmanager.domain.rest.dto.UrlResponse;
import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDataRepository;
import com.meli.challenge.urlmanager.model.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static com.meli.challenge.urlmanager.model.constants.ErrorCode.URL_DISABLED;
import static com.meli.challenge.urlmanager.model.constants.ErrorCode.URL_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

@TestPropertySource(locations = "classpath:application-test.properties")
class UrlRedirectServiceImplTest {
    private UrlDataRepository repository;
    private UrlRedirectServiceImpl urlRedirectService;
    private ApplicationEventPublisher eventPublisher;

    private static final String SHORT_URL = "meli.ly/123124";
    private static final String ORIGINAL_URL = "http://url-ejemplo.com";

    private UrlData urlData;

    @BeforeEach
    public void setUp() {
        eventPublisher = mock(ApplicationEventPublisher.class);
        repository = mock(UrlDataRepository.class);
        urlRedirectService = new UrlRedirectServiceImpl(eventPublisher, repository);
        LocalDateTime now = LocalDateTime.now();
        urlData = new UrlData("Id-1",SHORT_URL, ORIGINAL_URL, true, now, now);
    }

    @Test
    void testGetOriginalUrl_WhenUrlExistsAndIsEnabled_ShouldReturnUrlResponse() {
        Mockito.when(repository.findByShortUrl(SHORT_URL))
                .thenReturn(Mono.just(urlData));

        Mono<UrlResponse> result = urlRedirectService.getOriginalUrl(SHORT_URL);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getOriginalUrl().equals(ORIGINAL_URL))
                .verifyComplete();

        Mockito.verify(eventPublisher, Mockito.times(1)).publishEvent(any(UrlAccessedEvent.class));
    }


    @Test
    void testGetOriginalUrl_WhenUrlNotFound_ShouldReturnError() {
        Mockito.when(repository.findByShortUrl(SHORT_URL))
                .thenReturn(Mono.empty());
        Mono<UrlResponse> result = urlRedirectService.getOriginalUrl(SHORT_URL);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ServiceException &&
                        ((ServiceException) throwable).getCode()==(URL_NOT_FOUND.getCode()))
                .verify();

        Mockito.verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void testGetOriginalUrl_WhenUrlIsDisabled_ShouldReturnError() {
        urlData.setEnabled(false);
        Mockito.when(repository.findByShortUrl(SHORT_URL))
                .thenReturn(Mono.just(urlData));
        Mono<UrlResponse> result = urlRedirectService.getOriginalUrl(SHORT_URL);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ServiceException &&
                        ((ServiceException) throwable).getCode()==(URL_DISABLED.getCode()))
                .verify();

        Mockito.verify(eventPublisher, never()).publishEvent(any());
    }
}