package com.example.product.controller;

import com.example.product.entity.InsertProduct;
import com.example.product.entity.Product;
import com.example.product.form.ProductForm;
import com.example.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/product-list")
    public String productList(Model model) {
        // ロジックをServiceに任せる
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable("id") int id,Model model) {
        if(productService.findById(id) == null){
            return "error";
        } else {
            model.addAttribute("product", productService.findById(id));
            return "product";
        }
    }

    @GetMapping("/product-add")
    public String index(@ModelAttribute("ProductForm") ProductForm ProductForm) {
        return "product-add";
    }

    @PostMapping("/product-add")
    public String productAdd(@Validated @ModelAttribute("ProductForm") ProductForm ProductForm, BindingResult bindingResult){
        //バリデーション
        if(bindingResult.hasErrors()) {
            return "product-add";
        }
        else{
            productService.insert(new InsertProduct(ProductForm.getName(),Integer.parseInt(ProductForm.getPrice())));
            return "redirect:product-list";
        }
    }

    @GetMapping("/product-update/{id}")
    public String index(@PathVariable("id") int id, @ModelAttribute("productForm") ProductForm ProductForm, Model model) {
        if(productService.findById(id) == null){
            return "error2";
        } else {
            model.addAttribute("product", productService.findById(id));
            Product product = productService.findById(id);
            ProductForm.setName(product.name());
            ProductForm.setPrice(String.valueOf(product.price()));
            return "product-update";
        }
    }

    @PostMapping("product-update/{id}")
    public String update(@PathVariable("id") int id, @Validated @ModelAttribute("productForm") ProductForm ProductForm, BindingResult bindingResult,Model model) {
        //バリデーション
        if(bindingResult.hasErrors()) {
            model.addAttribute("product", productService.findById(id));
            return "product-update";
        }
        else{
            productService.update(new Product(id,ProductForm.getName(), Integer.parseInt(ProductForm.getPrice())));
            return "redirect:/product-list";
        }
    }

    @RequestMapping(value = "product/{id}", params = "delete", method = RequestMethod.POST)
    public String delete1(@PathVariable("id") int id,Model model){
        if(productService.findById(id) == null){
            return "error";
        } else {
            model.addAttribute("product", productService.delete(id));
            return "redirect:/product-list";
        }
    }

    @RequestMapping(value = "product/{id}", params = "update", method = RequestMethod.POST)
    public String update2(){
        return "redirect:/product-update/{id}";
    }
}