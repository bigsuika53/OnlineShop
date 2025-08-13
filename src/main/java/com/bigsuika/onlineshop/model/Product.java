package com.bigsuika.onlineshop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "product entity")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "product id", example = "1")
    private Long id;
    @Schema(description = "product name", example = "prod A")
    private String name;
    @Schema(description = "product price", example = "9.99")
    private double price;
    @Schema(description = "product stock", example = "10")
    private int stock;

}
