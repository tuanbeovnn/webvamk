package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository extends JpaRepository<ProductEntity, Long>, IProductRepositoryCustom {
    //    List<ProductEntity> findAll(ProductSearchBuilder fieldSeach, Pageable pageable);
    //select * from product == findAll
    @Query(value = "SELECT * FROM products order by created_date DESC", nativeQuery = true)
    List<ProductEntity> findAllByNewest(Pageable pageable);

    @Query(value = "SELECT p.* FROM products as p INNER JOIN categories on categories.id = p.category_id " +
            "WHERE categories.code like ?1", nativeQuery = true)
    List<ProductEntity> findAllByCategory(String code, Pageable pageable);

    @Query(value = "SELECT count(*) FROM products as p INNER JOIN categories on categories.id = p.category_id " +
            "WHERE categories.code like ?1", nativeQuery = true)
    Long countByCategory(String code);// new

    @Query(value = "select * from products", nativeQuery = true)
    List<ProductEntity> findAllByProduct(Pageable pageable);


//    @Query(value = "SELECT * FROM products INNER JOIN categories on categories.id = products.category_id WHERE categories.code like ?1 and product_name like %?2%", nativeQuery = true)
//    List<ProductEntity> searchProductByCategory(String code, String name);

    @Query(value = "select p1.* from products p1, (select * from products where products.code like ?1 ) as p2 " +
            "where p1.price <= p2.price and p1.category_id=p2.category_id and p1.code <> p2.code order by price desc", nativeQuery = true)
    List<ProductEntity> findRelatedProductByCode(String code, Pageable pageable);

    @Query(value = "SELECT * FROM products where products.code like ?", nativeQuery = true)
    ProductEntity findProductByCode(String code);

    @Query(value = "SELECT COUNT(*) FROM products", nativeQuery = true)
    Long countByProduct();

    @Query(value = "SELECT count(*) FROM products inner join categories on categories.id = products.category_id WHERE products.name like %?1% and categories.code like ?2", nativeQuery = true)
    Long countByProductCode(String name, String code);// new

    @Query(value = "select * from products where products.name like %?1%", nativeQuery = true)
    List<ProductEntity> searchProductByName(String name, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM products where products.name like %?1%", nativeQuery = true)
    Long countProductByName(String name);

    @Query(value = "SELECT * FROM products inner join categories on categories.id = products.category_id WHERE products.name like %?1% and categories.code like ?2", nativeQuery = true)
    List<ProductEntity> searchProductNameByCategory(String name, String code, Pageable pageable);

    @Query(value = "SELECT * FROM products p inner join categories c on c.id = p.category_id WHERE c.code like ?", nativeQuery = true)
    List<ProductEntity> sortByCategories(String code, Pageable pageable);

    @Query(value = "SELECT * FROM products as p, (SELECT SUM(quantity) as quantity, product_id FROM orderdetails GROUP by product_id order by quantity DESC) as ads WHERE p.id = ads.product_id", nativeQuery = true)
    List<ProductEntity> findAllByTrendding(Pageable pageable);

    @Query(value = "SELECT count(*) FROM products as p, (SELECT SUM(quantity) as quantity, product_id FROM orderdetails GROUP by product_id order by quantity DESC) as ads WHERE p.id = ads.product_id", nativeQuery = true)
    Long countByTrendding();


    @Query(value = "SELECT * FROM products WHERE products.discount > 0 AND products.end_time > now() order by products.discount desc", nativeQuery = true)
    List<ProductEntity> findAllByBestDeal(Pageable pageable);

    @Query(value = "SELECT count(*) FROM products WHERE products.discount > 0 AND products.end_time > now() order by products.discount desc", nativeQuery = true)
    Long countAllByCodeBestDeal();

    @Query(value = "SELECT * FROM products INNER join categories on categories.id = products.category_id WHERE products.discount > 0 and categories.code like ?1 order by products.discount desc", nativeQuery = true)
    List<ProductEntity> findAllByCodeBestDeal(Pageable pageable, String code);

    @Query(value = "SELECT count(*) FROM products INNER join categories on categories.id = products.category_id WHERE products.discount > 0 and categories.code like ?1 order by products.discount desc", nativeQuery = true)
    Long countByCodeBestDeal(String code);


    @Query(value = "SELECT * FROM products AS p, categories c, (SELECT SUM(quantity) AS quantity, product_id FROM orderdetails GROUP BY product_id ORDER BY quantity DESC ) AS ads WHERE p.id = ads.product_id and p.category_id = c.id and c.code like ?1", nativeQuery = true)
    List<ProductEntity> findAllByCategoryTrending(String code, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM products AS p, categories c, (SELECT SUM(quantity) AS quantity, product_id FROM orderdetails GROUP BY product_id ORDER BY quantity DESC ) AS ads WHERE p.id = ads.product_id and p.category_id = c.id and c.code like ?1", nativeQuery = true)
    Long countByByCategoryTrending(String code);// new

    @Query(value = "SELECT * from products WHERE products.quantity <=10", nativeQuery = true)
    List<ProductEntity> findAllByProductQuantity(Pageable pageable);

    @Query(value = "SELECT count(*) from products WHERE products.quantity <=10", nativeQuery = true)
    Long countByProductQuantity();// new
//    List<ProductEntity> findByName(String name, Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE products.brand_id= ?1", nativeQuery = true)
    List<ProductEntity> findAllByBrands(Long id, Pageable pageable);

    @Query(value = "SELECT count(*) FROM products WHERE products.brand_id= ?1", nativeQuery = true)
    Long countByBrands(Long id);

    @Query(value = "SELECT * FROM products p INNER JOIN categories c on p.category_id = c.id INNER JOIN brands b on p.brand_id = b.id WHERE c.code like ?1 AND b.code like ?2", nativeQuery = true)
    List<ProductEntity> findAllByCategoryAndBrand(String codeCate, String codeBrand, Pageable pageable);

    @Query(value = "SELECT count(*) FROM products p INNER JOIN categories c on p.category_id = c.id INNER JOIN brands b on p.brand_id = b.id WHERE c.code like ?1 AND b.code like ?2", nativeQuery = true)
    Long countByCategoryAndBrand(String codeCate, String codeBrand);


}
