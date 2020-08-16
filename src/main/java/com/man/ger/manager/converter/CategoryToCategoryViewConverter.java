package com.man.ger.manager.converter;

import com.man.ger.manager.entity.Category;

import com.man.ger.manager.view.CategoryView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryViewConverter implements Converter<Category, CategoryView> {


    @Override
    public CategoryView convert(Category category) {
        CategoryView categoryView= new CategoryView();
        categoryView.setId(category.getId());
        categoryView.setCategory(category.getCategory());
        return categoryView;
    }

}
