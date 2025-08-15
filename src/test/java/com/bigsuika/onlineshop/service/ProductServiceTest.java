package com.bigsuika.onlineshop.service;

import com.bigsuika.onlineshop.exception.QueryException;
import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // ProductServiceGetById unit test
    @Test
    public void PSGid_Test() throws Exception{
        Product product = new Product(1L, "prodA", 999.99, 10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals("prodA", result.getName());
    }

    // ProductServiceCreate unit test
    @Test
    public void PSC_Test() throws Exception{
        Product productInfo = new Product(null, "prodU", 999.99, 10);
        Product product = new Product(100L, "prodU", 999.99, 10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(productInfo);

        assertEquals(100L, result.getId());
    }

    // ProductServiceUpdate unit test
    @Test
    public void PSU_Test() throws Exception{
        Product product = new Product(100L, "prodU", 999.99, 10);
        Product productInfo = new Product(null, "prodV", 233.33, 20);

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(100L, productInfo);

        assertEquals("prodV", result.getName());
        assertEquals(233.33, result.getPrice());
    }

    // ProductServiceDelete unit test
    @Test
    public void PSD_Test() throws Exception{
        productRepository.deleteById(100L);

        verify(productRepository).deleteById(100L);
    }

    // ProductServiceIdNotFound unit test
    @Test
    public void PSGid_NotFound_Test() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(QueryException.class, () -> {
            productService.getProductById(999L);
        });
    }

}
