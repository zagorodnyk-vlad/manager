package com.man.ger.manager.service;

import com.man.ger.manager.converter.CategoryToCategoryViewConverter;
import com.man.ger.manager.dao.CategoryRepository;
import com.man.ger.manager.entity.Category;
import com.man.ger.manager.entity.CategoryEnumStatus;
import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.exseption.NotFoundCategoryExseption;
import com.man.ger.manager.view.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryToCategoryViewConverter categoryToCategoryViewConverter;

    public Long addCategory(String category) {
        return categoryRepository.save(new Category(category)).getId();

    }

    public Category fingById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category findByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (name == null) {
            throw new NotFoundCategoryExseption();
        }
        return category;
    }

    public CategoryView findByIdView(Long id){
      Category category = fingById(id);
      return categoryToCategoryViewConverter.convert(category);
    }
    public void deleteCategory(String name){
     categoryRepository.updateStatus(name,CategoryEnumStatus.DELETE);
}
}