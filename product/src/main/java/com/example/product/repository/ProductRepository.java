package com.example.product.repository;

import com.example.product.entity.InsertProduct;
import com.example.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements IProductRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products ORDER BY id",
                    new DataClassRowMapper<>(Product.class));
    }

    @Override
    public Product findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM products WHERE id = :id",
                param,
                new DataClassRowMapper<>(Product.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int insert(InsertProduct product) {
        var param = new MapSqlParameterSource();
        param.addValue("name", product.name());
        param.addValue("price", product.price());
        return jdbcTemplate.update("INSERT INTO products (name, price) VALUES(:name, :price)", param);
    }
}