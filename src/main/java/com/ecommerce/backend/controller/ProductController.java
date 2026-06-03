package com.ecommerce.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ADMIN : Add Product
    @PostMapping("/admin/products")
    public ProductDTO addProduct(@Valid @RequestBody ProductDTO dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());

        Product savedProduct = productService.addProduct(product);

        return convertToDTO(savedProduct);
    }

    // USER : Get All Products
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {

        return productService.getAllProducts()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // USER : Get Product By Id
    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        return convertToDTO(product);
    }

    // ADMIN : Update Product
    @PutMapping("/admin/products/{id}")
    public ProductDTO updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());

        Product updatedProduct = productService.updateProduct(id, product);

        return convertToDTO(updatedProduct);
    }

    // ADMIN : Delete Product
    @DeleteMapping("/admin/products/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }

    // Helper Method
    private ProductDTO convertToDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());

        return dto;
    }
}