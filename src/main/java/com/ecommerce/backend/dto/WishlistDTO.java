package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotNull;

public class WishlistDTO {

    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    public WishlistDTO() {
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}