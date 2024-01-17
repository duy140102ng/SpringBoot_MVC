package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String category(Model model) {
        List<Category> list = categoryService.getAll();
        model.addAttribute("list", list);
        return "category/index";
    }

    @GetMapping("/add-category")
    public String save(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/add";
    }

    @PostMapping("/add-category")
    public String create(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/category";
    }

    @GetMapping("/category/delete-category/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/category";
    }

    @GetMapping("/category/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "/category/update";
    }

    @PostMapping("/update-category")
    public String update(@ModelAttribute("category") Category category, RedirectAttributes redirectAttrs) {
        if (categoryService.save(category)) {
            redirectAttrs.addFlashAttribute("success", "Cập nhật thành công");
            return "redirect:/category";
        }
        return "redirect:/category";
    }
}
