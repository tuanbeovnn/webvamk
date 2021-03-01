package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

}
