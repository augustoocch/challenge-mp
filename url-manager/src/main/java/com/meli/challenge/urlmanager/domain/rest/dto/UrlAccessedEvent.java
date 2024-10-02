package com.meli.challenge.urlmanager.domain.rest.dto;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlAccessedEvent {
    private UrlData urlData;

}