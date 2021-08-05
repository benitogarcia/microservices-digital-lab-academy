package com.microservices.academy.product.service;

import com.microservices.academy.product.entity.Category;
import com.microservices.academy.product.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findByCategory(Long categoryId);

    Product findById(Long id);

    Product save(Product product);

    Product deleteById(Long id);

    Product updateStock(Long id, int stock);

}
