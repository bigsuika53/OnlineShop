package com.bigsuika.onlineshop.service;

import com.bigsuika.onlineshop.exception.QueryException;
import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new QueryException("product not found, ID: "+id)
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(null); // Force id to be null
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product oldProduct = getProductById(id);
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setStock(product.getStock());
        return productRepository.save(oldProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }
}
