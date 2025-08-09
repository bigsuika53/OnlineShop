package com.bigsuika.onlineshop.controller;

import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;

import com.bigsuika.onlineshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    // private ProductRepository productRepository;
    private ProductService productService;

    // ProductController unit test
    @Test
    public void pC_Test() throws Exception{
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

    // page function test
    @Test
    public void page_Test() throws Exception{
        Product product1 = new Product(1, "prodA", 999.99, 10);
        Product product2 = new Product(2, "prodC", 899.99, 15);
        List<Product> products = Arrays.asList(product1, product2);

        Pageable p = Pageable.ofSize(2).withPage(0);
        Page<Product> result = new PageImpl<>(products, p, products.size());

        when(productService.getAllProducts(any(Pageable.class))).thenReturn(result);

        mockMvc.perform(get("/api/product/page")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("prodA"))
                .andExpect(jsonPath("$.content[1].name").value("prodC"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}
