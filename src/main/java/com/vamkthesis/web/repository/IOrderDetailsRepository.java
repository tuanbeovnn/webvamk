package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailsRepository extends JpaRepository<OrderDetailEntity, Long> {
}
