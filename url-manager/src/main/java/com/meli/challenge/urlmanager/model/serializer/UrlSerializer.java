package com.meli.challenge.urlmanager.model.serializer;

import com.meli.challenge.urlmanager.model.entity.UrlData;
import org.apache.kafka.common.serialization.Serializer;

public class UrlSerializer implements Serializer<UrlData> {

    @Override
    public byte[] serialize(String s, UrlData urlData) {
        String serializedData = urlData.getShortUrl() + ","
                + urlData.getOriginalUrl() + ","
                + "," + urlData.getCreatedAt();
        return serializedData.getBytes();
    }
}
