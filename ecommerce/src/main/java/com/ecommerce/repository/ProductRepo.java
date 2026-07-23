package com.ecommerce.repository;


import com.ecommerce.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
    @Query(
            value = "SELECT * FROM (SELECT p.*, ROWNUM rnum FROM products p WHERE ROWNUM <= :endRow order by p.price desc) WHERE rnum > :startRow",
            nativeQuery = true
    )
    List<Product> findProductsWithPagination(@Param("startRow") int startRow, @Param("endRow") int endRow);

}

