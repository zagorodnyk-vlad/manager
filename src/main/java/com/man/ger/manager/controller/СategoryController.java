package com.man.ger.manager.controller;

import com.man.ger.manager.service.CategoryService;
import com.man.ger.manager.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class СategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/addcategory")
    public Long addCategory(@RequestParam (value = "category") String category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/findbyid")
    public String findId(@RequestParam Long id) {
        return categoryService.fingById(id).getCategory();
    }

    @GetMapping("/findbyname")
    public Long findName(String name) {
        return categoryService.findByName(name).getId();
    }
    @GetMapping("/getcategory")
    public CategoryView categoryView(@RequestParam Long id){
        return  categoryService.findByIdView(id);
    }

    @DeleteMapping("/delet")
    public void deleteCategory(@RequestParam String name){
 categoryService.deleteCategory(name);
    }
}
