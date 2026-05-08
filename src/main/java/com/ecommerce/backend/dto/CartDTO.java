package com.ecommerce.backend.dto;

public class CartDTO {

    private Long id;
    private Long productId;
    private int quantity;

    public CartDTO() {
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

//useremail should come form JWT token not form frontend 