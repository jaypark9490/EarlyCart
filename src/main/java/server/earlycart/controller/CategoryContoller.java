package server.earlycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.earlycart.model.Category;
import server.earlycart.service.CategoryService;

import java.util.ArrayList;

@RestController
public class CategoryContoller {
    @Autowired
    CategoryService categoryService;

    @GetMapping("category")
    public ArrayList<Category> userRegister() {
        return categoryService.getCategory();
    }
}