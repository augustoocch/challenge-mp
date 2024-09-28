package com.meli.challenge.statisticts.model.business;

import com.meli.challenge.statisticts.domain.rest.dto.UrlDataDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.time.LocalDateTime;

public class UrlDeserializer implements Deserializer<UrlDataDto> {

    @Override
    public UrlDataDto deserialize(String s, byte[] bytes) {
        String data = new String(bytes);
        String[] parts = data.split(",");
        LocalDateTime createdAt = LocalDateTime.parse(parts[3]);
        LocalDateTime lastAccessedAt = LocalDateTime.parse(parts[4]);
        return new UrlDataDto(parts[1], parts[0], true, createdAt, createdAt,
                Integer.parseInt(parts[2]), lastAccessedAt);
    }
}
