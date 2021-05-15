package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.RoomEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoomRepository extends JpaRepository<RoomEntity, Long> {


    @Query(value = "SELECT * FROM rooms WHERE rooms.staff_id = 0 or rooms.staff_id=?", nativeQuery = true)
    List<RoomEntity> findAllByMessageNotRead(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM rooms WHERE rooms.room_id = ?", nativeQuery = true)
    RoomEntity findAllByRoomId(String id);


}
