package com.example.product.controller;

import com.example.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("product-list")
    public String productList(Model model) {
        // ロジックをServiceに任せる
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping("product/{id}")
    public String product(@PathVariable("id") int id,Model model) {
        if(productService.findById(id) == null){
            return "error";
        } else {
            model.addAttribute("product", productService.findById(id));
            return "product";
        }
    }
}