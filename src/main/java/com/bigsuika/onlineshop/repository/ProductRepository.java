package com.bigsuika.onlineshop.repository;


import com.bigsuika.onlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // jpa fundamental functions
}
