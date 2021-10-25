package com.geekbrains.geekmarketwinter.controllers;

import com.geekbrains.geekmarketwinter.entites.Category;
import com.geekbrains.geekmarketwinter.entites.Product;
import com.geekbrains.geekmarketwinter.entites.ProductImage;
import com.geekbrains.geekmarketwinter.services.CategoryService;
import com.geekbrains.geekmarketwinter.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/edit/0")
    public String newProduct(Model model) {
        Product product = new Product();
        product.setId(0L);
        model.addAttribute("product", product);
        ProductImage productImage = new ProductImage();
        model.addAttribute("productImage", productImage);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-edit";
    }

    @PostMapping("/add")
    public String addProduct(Product product, ProductImage productImage, String cat) {

        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            if (category.getTitle().equals(cat))
                product.setCategory(category);
        }
        productImage.setProduct(product);
        product.addImage(productImage);

        productService.saveProduct(product);
        return "redirect:/shop";
    }
}
