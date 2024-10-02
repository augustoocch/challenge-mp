package com.meli.challenge.urlmanager.model.serializer;

import com.meli.challenge.urlmanager.domain.rest.dto.UrlDataDto;
import org.apache.kafka.common.serialization.Serializer;

public class UrlSerializer implements Serializer<UrlDataDto> {

    @Override
    public byte[] serialize(String s, UrlDataDto urlData) {
        String serializedData = urlData.getUUID() + ","
                + urlData.getShortUrl() + ","
                + urlData.getOriginalUrl() + ","
                + urlData.getCreatedAt();
        return serializedData.getBytes();
    }
}
