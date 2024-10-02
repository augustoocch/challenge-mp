package com.meli.challenge.statisticts.model.entity.repository;

import com.meli.challenge.statisticts.model.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MessageRepository {
    Optional<Message> findByUUID(String UUID);
    void save(Message message);
}