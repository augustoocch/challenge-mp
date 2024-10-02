package com.meli.challenge.statisticts.model.deserializer;

import com.meli.challenge.statisticts.model.dto.UrlDataDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.time.LocalDateTime;

public class UrlDeserializer implements Deserializer<UrlDataDto> {

    @Override
    public UrlDataDto deserialize(String s, byte[] bytes) {
        String data = new String(bytes);
        String[] parts = data.split(",");
        LocalDateTime createdAt = LocalDateTime.parse(parts[3]);
        return new UrlDataDto(parts[0], parts[1], parts[2], createdAt);
    }
}
