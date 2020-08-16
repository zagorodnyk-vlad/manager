package com.man.ger.manager.converter;

import com.man.ger.manager.dto.ExpensesDto;
import com.man.ger.manager.entity.Category;
import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.exseption.NotFoundCategoryExseption;
import com.man.ger.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpensesDtoToExpenses implements Converter<ExpensesDto, Expenses> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Expenses convert(ExpensesDto expensesDto) {

        Expenses expenses= new Expenses();
        expenses.setSum(expensesDto.getSum());
        expenses.setDate(expensesDto.getDate());
        Category category= categoryService.fingById(expensesDto.getCategoryId());
        if(category==null){
            throw new NotFoundCategoryExseption("Category with id"+expensesDto.getCategoryId()+" not found");
        }
        expenses.setCategory(category);
        return expenses;
    }
}
