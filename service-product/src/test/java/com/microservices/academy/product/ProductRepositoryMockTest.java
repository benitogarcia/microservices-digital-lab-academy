package com.microservices.academy.product;

import com.microservices.academy.product.entity.Category;
import com.microservices.academy.product.entity.Product;
import com.microservices.academy.product.repository.CategoryRepository;
import com.microservices.academy.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findByCategory(){

        Category _category = new Category();
        _category.setId(1L);
        _category.setName("Computer");

        categoryRepository.save(_category);

        Product _product = new Product();
        _product.setId(1L);
        _product.setName("Laptop Acer");
        _product.setPrice(123.6);
        _product.setDescription("Laptop portail ultra ligera");
        _product.setStock(3);
        _product.setCreateAt(new Date());
        _product.setStatus("CREATED");
        _product.setCategory(_category);

        productRepository.save(_product);

        List<Product> _products = productRepository.findByCategory(_category);

        //Assertions.assertEquals(_products.size(), 5);

        /*for (Product product: _products) {
            System.out.println(product.toString());
        }*/
    }

}
