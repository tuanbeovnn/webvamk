package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByName(String name);
    UserEntity findOneByEmail(String email);

}
