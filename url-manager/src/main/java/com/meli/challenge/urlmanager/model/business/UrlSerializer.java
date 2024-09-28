package com.meli.challenge.urlmanager.model.business;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import org.apache.kafka.common.serialization.Serializer;

import java.time.LocalDateTime;
import java.util.Date;

public class UrlSerializer implements Serializer<UrlData> {

    @Override
    public byte[] serialize(String s, UrlData urlData) {
        LocalDateTime lastAccessedAt = LocalDateTime.now();
        String dataCount = String.valueOf(urlData.getAccessCount());
        String serializedData = urlData.getOriginalUrl() + ","
                + urlData.getShortUrl() + ","
                + dataCount
                + "," + urlData.getCreatedAt()
                + "," + lastAccessedAt;
        return serializedData.getBytes();
    }
}
