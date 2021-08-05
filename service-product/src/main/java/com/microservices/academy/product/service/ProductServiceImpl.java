package com.microservices.academy.product.service;

import com.microservices.academy.product.entity.Category;
import com.microservices.academy.product.entity.Product;
import com.microservices.academy.product.repository.CategoryRepository;
import com.microservices.academy.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null)
            return  null;

        return productRepository.findByCategory(category);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product deleteById(Long id) {

        Product _product = productRepository.findById(id).orElse(null);

        if (_product != null){
            productRepository.deleteById(id);
        }

        return _product;
    }

    @Override
    public Product updateStock(Long id, int stock) {
        Product _product = productRepository.findById(id).orElse(null);
        if (_product == null){
            return null;
        }

        _product.setStock(_product.getStock() + stock);

        return productRepository.save(_product);
    }
}
