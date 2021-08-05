package com.microservices.academy.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.academy.product.entity.Product;
import com.microservices.academy.product.repository.ProductRepository;
import com.microservices.academy.product.service.ProductService;
import com.microservices.academy.product.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> products(@RequestParam(name = "categoryId", required = false) Long categoryId){

        List<Product> _products =new ArrayList<>();

        if (categoryId == null) {
            _products = productService.findAll();
            if (_products.isEmpty()){
                return  ResponseEntity.noContent().build();
            }
        }else {
            _products = productService.findByCategory(categoryId);
            if (_products == null || _products.isEmpty()){
                return  ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(_products);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable(name = "id", required = true) Long id){

        Product _product = productService.findById(id);

        if (_product == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(_product);

    }

    @PostMapping("/save")
    public ResponseEntity<Product> create(@Valid @RequestBody Product product, BindingResult br){

        if (br.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(br));
            //return ResponseEntity.unprocessableEntity().build();
        }

        Product _product = productService.save(product);

        if (_product==null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(_product);

    }

    private String formatMessage(BindingResult br) {

        //Generar la lista
        List<Map<String, String>> errors = br.getFieldErrors().stream()
                .map( err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        // Setear los datos
        ErrorMessage em = new ErrorMessage();
        em.setCode("401");
        em.setMessages(errors);

        //Convertir el objeto a json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(em);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;

    }
}
