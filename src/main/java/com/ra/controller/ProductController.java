package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    @Value("${path-upload}")
    private String pathUpload;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/product")
    public String product(Model model){
        List<Product> list = productService.getAll();
        model.addAttribute("list", list);
        return "product/index";
    }
    @GetMapping("/add-product")
    public String save(Model model){
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/add";
    }
    @PostMapping("/add-product")
    public String create(@ModelAttribute("product") Product product, @RequestParam("img")MultipartFile file){
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(pathUpload + fileName));
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/product";
    }
    @GetMapping("/product/delete-category/{id}")
    public String delete(@PathVariable("id") Long id){
        productService.delete(id);
        return "redirect:/product";
    }
    @GetMapping("/product/update/{id}")
    public String update(@PathVariable Long id, Model model){
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/update";
    }
    @PostMapping("/update-product")
    public String update(@ModelAttribute ("product") Product product, @RequestParam("img") MultipartFile file){
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(pathUpload + fileName));
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/product";
    }
}
