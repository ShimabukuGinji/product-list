package com.example.product.service;

import com.example.product.entity.InsertProduct;
import com.example.product.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    List<Product> findAll();

    Product findById(int id);

    int insert(InsertProduct product);
}
