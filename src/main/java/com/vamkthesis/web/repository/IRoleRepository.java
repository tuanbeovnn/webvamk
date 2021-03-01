package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findOneByName(String name);
}
