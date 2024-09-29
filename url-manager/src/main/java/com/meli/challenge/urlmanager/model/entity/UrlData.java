package com.meli.challenge.urlmanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "shortened_urls")
@CompoundIndexes({
        @CompoundIndex(name = "unique_shortUrl", def = "{'shortUrl': 1}", unique = true)
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlData {
    @Id
    private String id;
    private String shortUrl;
    private String originalUrl;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}