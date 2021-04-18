package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByName(String name);
    UserEntity findOneByEmail(String email);

    @Query(value = "SELECT DISTINCT u.* , ( SELECT group_concat(r.name) FROM user_roles ur LEFT JOIN roles r ON ur.role_id = r.id WHERE 1=1 AND u.id = ur.user_id ) AS role FROM users u INNER JOIN user_roles ur ON u.id = ur.user_id INNER JOIN roles r ON ur.role_id = r.id WHERE 1=1 AND r.name = 'STAFF' or r.name='ACCESS_SWAGGER' or r.name='ADMIN'", nativeQuery = true)
    List<UserEntity> findAllByRoles(Pageable pageable);

//    @Query(value = "SELECT COUNT(*) FROM products AS p, categories c, (SELECT SUM(quantity) AS quantity, product_id FROM orderdetails GROUP BY product_id ORDER BY quantity DESC ) AS ads WHERE p.id = ads.product_id and p.category_id = c.id and c.code like ?1", nativeQuery = true)
//    Long countByByCategoryTrending(String code);// new

    @Query(value = "SELECT COUNT(e.id) FROM (SELECT DISTINCT u.* , ( SELECT group_concat(r.name) FROM user_roles ur LEFT JOIN roles r ON ur.role_id = r.id WHERE 1=1 AND u.id = ur.user_id ) AS role FROM users u INNER JOIN user_roles ur ON u.id = ur.user_id INNER JOIN roles r ON ur.role_id = r.id WHERE 1=1 AND r.name = 'STAFF' or r.name='ACCESS_SWAGGER' or r.name='ADMIN') e", nativeQuery = true)
    Long countALLByRoles();
}
