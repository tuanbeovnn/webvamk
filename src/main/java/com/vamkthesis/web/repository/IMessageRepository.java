package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMessageRepository extends JpaRepository<MessageEntity, Long> {
    @Query(value = "SELECT * FROM message_entity WHERE message_entity.room_number= ? order by created_date DESC", nativeQuery = true)
    List<MessageEntity> findAllByNewest(String room, Pageable pageable);


}
