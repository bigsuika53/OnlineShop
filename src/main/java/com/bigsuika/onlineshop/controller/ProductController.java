package com.bigsuika.onlineshop.controller;

import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product API")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public List<Product> findAll() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get all products by page")
    @GetMapping("/page")
    public Page<Product> findAllPage
            (@Parameter(description = "page(start from 0)", example = "0")
             @RequestParam(defaultValue = "0") int page,

             @Parameter(description = "NumOfProducts per page", example = "10")
             @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(Pageable.ofSize(size).withPage(page));
    }

    @Operation(summary = "Find product by id")
    @GetMapping("/{id}")
    public Product findProductById(@Parameter(description = "product id", required = true, example = "1")
                                   @PathVariable Long id){
        return productService.getProductById(id);
    }

    @Operation(summary = "Create new product",
            description = "The id part is generated using Strategy=IDENTITY, just ignore")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(
            @Parameter(description = "product info", required = true)
            @RequestBody Product product){
        return productService.createProduct(product);
    }

    @Operation(summary = "Edit exist product")
    @PutMapping("/{id}")
    public Product updateProduct(
            @Parameter(description = "product id", required = true, example = "1")
            @PathVariable Long id,

            @Parameter(description = "product info", required = true)
            @RequestBody Product product){
    return  productService.updateProduct(id, product);}

    @Operation(summary = "Delete exist product")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @Parameter(description = "product id", required = true, example = "1")
            @PathVariable Long id){
        productService.deleteProduct(id);
    }
}
