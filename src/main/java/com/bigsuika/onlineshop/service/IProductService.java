package com.bigsuika.onlineshop.service;

import com.bigsuika.onlineshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);

    List<Product> getAllProducts();
    Page<Product> getAllProducts(Pageable pageable);
}
