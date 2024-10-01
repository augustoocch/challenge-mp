package com.meli.challenge.urlmanager.model.service.impl;

import static com.meli.challenge.urlmanager.model.constants.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import com.meli.challenge.urlmanager.model.entity.repository.UrlDataRepository;
import com.meli.challenge.urlmanager.model.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@TestPropertySource(locations = "classpath:application-test.properties")
public class UrlManagementServiceImplTest {
    private UrlDataRepository repository;
    private UrlManagementServiceImpl service;

    @BeforeEach
    public void setUp() {
        repository = mock(UrlDataRepository.class);
        service = new UrlManagementServiceImpl(repository);
    }

    @Test
    public void createUrlData_whenUrlExists_shouldThrowException() {
        String existingUrl = "https://existing-url.com";
        doReturn(Mono.just(new UrlData()))
                .when(repository).findByOriginalUrl(anyString());

        StepVerifier.create(service.createUrlData(existingUrl))
                .expectErrorMatches(throwable -> throwable instanceof ServiceException
                        && ((ServiceException) throwable).getCode() == (URL_ALREADY_EXISTS.getCode()))
                .verify();

        verify(repository, times(1)).findByOriginalUrl(eq(existingUrl));
    }

    @Test
    public void createUrlData_whenUrlNotExists_shouldSaveNewUrl() {
        doReturn(Mono.empty())
                .when(repository).findByOriginalUrl(anyString());

        String newUrl = "https://new2-url.com";
        UrlData url= new UrlData();
        url.setOriginalUrl(newUrl);

        doReturn(Mono.just(url))
                .when(repository).save(any(UrlData.class));

        StepVerifier.create(service.createUrlData(newUrl))
                .expectNextMatches(urlData -> urlData.getOriginalUrl().equals(newUrl))
                .verifyComplete();

        verify(repository, times(1)).findByOriginalUrl(newUrl);
        verify(repository, times(1)).save(any(UrlData.class));
    }


    @Test
    public void getUrlData_whenShortUrlExists_should_return_element() {
        String shortUrl = "shortUrl";
        UrlData urlData = new UrlData();
        urlData.setShortUrl(shortUrl);

        doReturn(Mono.just(urlData))
                .when(repository).findByShortUrl(eq(shortUrl));

        StepVerifier.create(service.getUrlData(shortUrl))
                .expectNextMatches(data -> data.getShortUrl().equals(shortUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
    }

    @Test
    public void getUrlData_whenShortUrlNotExists_shouldReturnEmpty() {
        String shortUrl = "shortUrl";

        doReturn(Mono.empty())
                .when(repository).findByShortUrl(eq(shortUrl));

        StepVerifier.create(service.getUrlData(shortUrl))
                .expectErrorMatches(throwable -> throwable instanceof ServiceException
                        && ((ServiceException) throwable).getCode() == (URL_NOT_FOUND.getCode()))
                .verify();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
    }

    @Test
    public void updateUrl_whenUrlExists_shouldUpdateAndSave() {
        String shortUrl = "shortUrl.ly";
        String newUrl = "https://new-url.com";
        UrlData urlData = new UrlData();
        urlData.setShortUrl(shortUrl);
        urlData.setOriginalUrl("https://original.com");

        doReturn(Mono.just(urlData))
                .when(repository).findByShortUrl(anyString());

        UrlData updatedUrl = new UrlData();
        updatedUrl.setShortUrl(shortUrl);
        updatedUrl.setOriginalUrl(newUrl);

        doReturn(Mono.just(updatedUrl))
                .when(repository).save(any(UrlData.class));

        StepVerifier.create(service.updateUrl(shortUrl, newUrl))
                .expectNextMatches(url -> url.getOriginalUrl().equals(newUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, times(1)).save(any(UrlData.class));
    }

    @Test
    public void updateUrl_whenUrlNotExists_shouldReturnError() {
        String shortUrl = "shortUrl.ly";
        String newUrl = "https://new-url.com";

        doReturn(Mono.empty())
                .when(repository).findByShortUrl(anyString());

        StepVerifier.create(service.updateUrl(shortUrl, newUrl))
                .expectErrorMatches(throwable -> throwable instanceof ServiceException
                        && ((ServiceException) throwable).getCode() == (URL_NOT_FOUND.getCode()))
                .verify();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, never()).save(any(UrlData.class));
    }

    @Test
    public void disableUrl_whenUrlExists_shouldDisableAndSave() {
        String shortUrl = "shortUrl";
        UrlData urlData = new UrlData();
        urlData.setEnabled(true);
        urlData.setShortUrl(shortUrl);

        doReturn(Mono.just(urlData))
                .when(repository).findByShortUrl(anyString());

        doReturn(Mono.just(urlData))
                .when(repository).save(any(UrlData.class));

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

        doReturn(Mono.just(urlData))
                .when(repository).findByShortUrl(anyString());

        doReturn(Mono.just(urlData))
                .when(repository).save(any(UrlData.class));

        StepVerifier.create(service.enableUrl(shortUrl))
                .verifyComplete();

        verify(repository, times(1)).findByShortUrl(eq(shortUrl));
        verify(repository, times(1)).save(any(UrlData.class));
        assertTrue(urlData.isEnabled());
    }
}
