package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageRepository extends JpaRepository<MessageEntity, Long> {
}
