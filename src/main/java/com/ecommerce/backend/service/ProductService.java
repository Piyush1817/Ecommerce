package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    

    // ✅ Add Product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // ✅ Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ✅ Get Product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ✅ Update Product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(product);
    }

    // ✅ Delete Product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}