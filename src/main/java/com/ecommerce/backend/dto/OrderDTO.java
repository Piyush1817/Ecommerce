package com.ecommerce.backend.dto;

public class OrderDTO {

    private Long id;
    private Long productId;
    private int quantity;

    public OrderDTO() {
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