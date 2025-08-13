package com.bigsuika.onlineshop.controller;

import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;

import com.bigsuika.onlineshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @Autowired
    private ObjectMapper objectMapper;

    // ProductControllerGet unit test
    @Test
    public void PCG_Test() throws Exception{
        Product product1 = new Product(1L, "prodU", 999.99, 10);
        Product product2 = new Product(2L, "prodV", 899.99, 15);
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("prodU"))
                .andExpect(jsonPath("$[1].name").value("prodV"));
    }

    // ProductControllerPage unit test
    @Test
    public void page_Test() throws Exception{
        Product product1 = new Product(1L, "prodA", 999.99, 10);
        Product product2 = new Product(2L, "prodC", 899.99, 15);
        List<Product> products = Arrays.asList(product1, product2);
        Page<Product> result = new PageImpl<>(products);

        when(productService.getAllProducts(any(Pageable.class))).thenReturn(result);

        mockMvc.perform(get("/api/product/page?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("prodA"))
                .andExpect(jsonPath("$.content[1].name").value("prodC"));
    }

    // ProductControllerGetById unit test
    @Test
    public void PCGid_Test() throws Exception{
        Product product = new Product(1L, "prodA", 999.99, 10);
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("prodA"));
    }

    // ProductControllerCreate unit test
    @Test
    public void PCC_Test() throws Exception{
        Product productInfo = new Product(null, "prodU", 999.99, 10);
        Product product = new Product(100L, "prodU", 999.99, 10);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productInfo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("100"))
                .andExpect(jsonPath("$.name").value("prodU"));
    }

    // ProductControllerUpdate unit test
    @Test
    public void PCU_Test() throws Exception{
        Product product = new Product(100L, "prodU", 999.99, 10);

        when(productService.updateProduct(eq(100L), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/product/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("100"))
                .andExpect(jsonPath("$.name").value("prodU"));

    }

    // ProductControllerDelete unit test
    @Test
    public void PCD_Test() throws Exception{
        when(productService.getProductById(1L)).thenReturn(null);// 遗漏返回值将报错

        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isNoContent());
    }

}
