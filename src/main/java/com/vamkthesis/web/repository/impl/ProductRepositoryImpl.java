package com.vamkthesis.web.repository.impl;


import com.vamkthesis.web.builder.ProductSearchBuilder;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.helper.MapToSqlSearch;
import com.vamkthesis.web.helper.ObjectToMap;
import com.vamkthesis.web.repository.ProductRepositoryCustom;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductEntity> findAll(ProductSearchBuilder fieldSearch, Pageable pageable) {

        String sql = "select p from ProductEntity p WHERE 1=1" + buildQuery(fieldSearch, "p");
        Query query = em.createQuery(sql);
        query.setFirstResult((pageable.getPageNumber()-1)*pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<ProductEntity> results = (List<ProductEntity>) query.getResultList();
        return results;
    }

    private String buildQuery(ProductSearchBuilder fieldSearch, String prefix) {
        String specicalSql = getSpecicalSqlString(fieldSearch);
        ProductSearchBuilder singleFieldBuilder = new ProductSearchBuilder.Builder()
                .setProductName(fieldSearch.getProductName())
                .setWarranty(fieldSearch.getWarranty())
                .setOrigin(fieldSearch.getOrigin())
                .build();
        Map<String, Object> map = ObjectToMap.toMap(singleFieldBuilder);
        String where = MapToSqlSearch.toSql(map,prefix);
       return specicalSql;
    }

    private String getSpecicalSqlString(ProductSearchBuilder fieldSearch) {
        StringBuilder sql = new StringBuilder();
        String prefix = "p.";

        if (fieldSearch.getPriceFrom() != null) {
            sql.append(" AND " + prefix + "price >="+ fieldSearch.getPriceFrom());
        }
        if (fieldSearch.getPriceTo() != null) {
            sql.append(" AND " + prefix + "price <="+ fieldSearch.getPriceTo());
        }
        return sql.toString();
    }

//    @Override
//    public List<ProductEntity> findAll(ProductSearchBuilder fieldSearch, Pageable pageable) {
//        StringBuilder result = new StringBuilder("select A from ProductEntity A");
//        result.append(" WHERE A.productName LIKE ?1 AND A.origin like ?2 AND A.warranty like ?3");
//        if (fieldSearch.getPriceFrom() != null) {
//            result.append(" AND A.price >= ?4");
//        }
//        if (fieldSearch.getPriceTo() != null) {
//            result.append(" AND A.price <= ?5");
//        }
////        Query query = em.createQuery(result.toString());
//
//        Query query = em.createQuery(result.toString());
//        em.createNativeQuery(result.toString());
//        query.setFirstResult((pageable.getPageNumber()-1) * pageable.getPageSize());
//        query.setMaxResults(pageable.getPageSize());
//        query.setParameter(1,"%"+fieldSearch.getProductName()+"%");
//        query.setParameter(2,"%"+fieldSearch.getOrigin()+"%");
//        query.setParameter(3,"%"+fieldSearch.getWarranty()+"%");
//        if (fieldSearch.getPriceFrom() != null) {
//            query.setParameter(4,fieldSearch.getPriceFrom());
//        }
//        if (fieldSearch.getPriceTo() != null) {
//            query.setParameter(5,fieldSearch.getPriceTo());
//        }
//        List<ProductEntity> results = query.getResultList();
//        return results;
////        return query.getResultList();
//    }
}
