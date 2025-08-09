// ProductController unit test
package com.bigsuika.onlineshop.controller;

import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;

import com.bigsuika.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    //private ProductRepository productRepository;
    private ProductService productService;

    @Test
    public void getAllProducts_ShouldReturnProducts() throws Exception{
        Product product1 = new Product(1, "prodA", 999.99, 10);
        Product product2 = new Product(2, "prodC", 899.99, 15);
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("prodA"))
                .andExpect(jsonPath("$[0].price").value(999.99))
                .andExpect(jsonPath("$[1].name").value("prodC"))
                .andExpect(jsonPath("$[1].price").value(899.99));
    }
}
