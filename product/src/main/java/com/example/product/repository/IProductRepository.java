package com.example.product.repository;

import com.example.product.entity.Product;
import java.util.List;

public interface IProductRepository {
    List<Product> findAll();

    Product findById(int id);
}
