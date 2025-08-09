package com.bigsuika.onlineshop.service;

import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void getAllProducts_ShouldReturnProducts() throws Exception{
        Product product1 = new Product(1, "prodA", 999.99, 10);
        Product product2 = new Product(2, "prodC", 899.99, 15);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(products, result);
        assertEquals(2, result.size());
        assertEquals("prodA", result.get(0).getName());
    }

}
