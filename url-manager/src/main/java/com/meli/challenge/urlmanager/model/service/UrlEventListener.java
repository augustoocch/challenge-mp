package com.meli.challenge.urlmanager.model.service;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlAccessedEvent;

public interface UrlEventListener {
    void handleUrlAccessed(UrlAccessedEvent event);
}
