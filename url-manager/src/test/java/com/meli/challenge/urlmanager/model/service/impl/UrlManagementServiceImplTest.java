package com.meli.challenge.urlmanager.model.service.impl;

import static com.meli.challenge.urlmanager.model.constants.ErrorCode.URL_ALREADY_EXISTS;
import static com.meli.challenge.urlmanager.model.constants.ErrorCode.URL_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reactor.core.publisher.Mono.when;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDataRepository;
import com.meli.challenge.urlmanager.model.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;



public class UrlManagementServiceImplTest {
    @Mock
    private UrlDataRepository repository;
    @InjectMocks
    private UrlManagementServiceImpl service;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUrlData_whenUrlExists_shouldThrowException() {
        String existingUrl = "https://existing-url.com";
        when(repository.findByOriginalUrl(eq(existingUrl)))
                .thenReturn(Mono.just(new UrlData()));

        StepVerifier.create(service.createUrlData(existingUrl))
                .expectErrorMatches(throwable -> throwable instanceof ServiceException
                        && ((ServiceException) throwable).getCode() == (URL_ALREADY_EXISTS.getCode()))
                .verify();

        verify(repository, times(1)).findByOriginalUrl(eq(existingUrl));
    }

    @Test
    public void createUrlData_whenUrlNotExists_shouldSaveNewUrl() {
        String newUrl = "https://new-url.com";
        when(repository.findByOriginalUrl(eq(newUrl)))
                .thenReturn(Mono.empty());
        when(repository.save(any(UrlData.class)))
                .thenReturn(Mono.just(new UrlData()));

        StepVerifier.create(service.createUrlData(newUrl))
                .expectNextMatches(urlData -> urlData.getOriginalUrl().equals(newUrl))
                .verifyComplete();

        verify(repository, times(1)).findByOriginalUrl(eq(newUrl));
        verify(repository, times(1)).save(any(UrlData.class));
    }

    @Test
    public void getUrlData_whenShortUrlExists_shouldIncrementAccessCount() {
        String shortUrl = "shortUrl";
        UrlData urlData = new UrlData();
        urlData.setShortUrl(shortUrl);

        when(repository.findByShortUrl(eq(shortUrl)))
                .thenReturn(Mono.just(urlData));
        when(repository.save(any(UrlData.class)))
                .thenReturn(Mono.just(urlData));

        StepVerifier.create(service.getUrlData(shortUrl))
                .expectNextMatches(data -> data.getShortUrl().equals(shortUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, times(1)).save(any(UrlData.class));
    }

    @Test
    public void getUrlData_whenShortUrlNotExists_shouldReturnEmpty() {
        String shortUrl = "shortUrl";
        when(repository.findByShortUrl(eq(shortUrl)))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.getUrlData(shortUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, never()).save(any(UrlData.class));
    }

    @Test
    public void updateUrl_whenUrlExists_shouldUpdateAndSave() {
        String id = "id";
        String newUrl = "https://new-url.com";
        UrlData urlData = new UrlData();
        urlData.setId(id);

        when(repository.findById(eq(id)))
                .thenReturn(Mono.just(urlData));
        when(repository.save(any(UrlData.class)))
                .thenReturn(Mono.just(urlData));

        StepVerifier.create(service.updateUrl(id, newUrl))
                .expectNextMatches(url -> url.getOriginalUrl().equals(newUrl))
                .verifyComplete();

        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).save(any(UrlData.class));
    }

    @Test
    public void updateUrl_whenUrlNotExists_shouldReturnError() {
        String id = "id";
        String newUrl = "https://new-url.com";

        when(repository.findById(eq(id)))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.updateUrl(id, newUrl))
                .expectErrorMatches(throwable -> throwable instanceof ServiceException
                        && ((ServiceException) throwable).getCode() == (URL_NOT_FOUND.getCode()))
                .verify();

        verify(repository, times(1)).findById(eq(id));
        verify(repository, never()).save(any(UrlData.class));
    }

    @Test
    public void disableUrl_whenUrlExists_shouldDisableAndSave() {
        String shortUrl = "shortUrl";
        UrlData urlData = new UrlData();
        urlData.setEnabled(true);
        urlData.setShortUrl(shortUrl);

        when(repository.findByShortUrl(eq(shortUrl)))
                .thenReturn(Mono.just(urlData));
        when(repository.save(any(UrlData.class)))
                .thenReturn(Mono.just(urlData));

        StepVerifier.create(service.disableUrl(shortUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, times(1)).save(any(UrlData.class));
        assertFalse(urlData.isEnabled());
    }

    @Test
    public void enableUrl_whenUrlExists_shouldEnableAndSave() {
        String shortUrl = "shortUrl";
        UrlData urlData = new UrlData();
        urlData.setEnabled(false);
        urlData.setShortUrl(shortUrl);

        when(repository.findByShortUrl(eq(shortUrl)))
                .thenReturn(Mono.just(urlData));
        when(repository.save(any(UrlData.class)))
                .thenReturn(Mono.just(urlData));

        StepVerifier.create(service.enableUrl(shortUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, times(1)).save(any(UrlData.class));
        assertTrue(urlData.isEnabled());
    }
}
