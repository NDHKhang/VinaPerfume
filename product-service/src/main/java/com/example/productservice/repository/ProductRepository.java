package com.example.productservice.repository;

import com.example.productservice.model.entity.Category;
import com.example.productservice.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingAndStatusIsTrue(String name, Pageable pageable);

    Page<Product> findByCategoryAndNameContainingAndStatusIsTrue(Category category, String name, Pageable pageable);

    @Query(value = """
            SELECT * FROM product p\s
            WHERE p.id != :productId AND p.category_id = :categoryId AND p.status = TRUE\s
            ORDER BY RAND() LIMIT :limit""", nativeQuery = true)
    List<Product> findByRelated(int categoryId, int productId, int limit);


    @Query(value = """
            SELECT * FROM product p\s   
            ORDER BY id DESC LIMIT :limit""", nativeQuery = true)
    List<Product> findByNew(int limit);

}
