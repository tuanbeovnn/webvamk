package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.EmailSubcribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmailRepository extends JpaRepository<EmailSubcribeEntity, Long> {
}
