package com.man.ger.manager.controller;

import com.man.ger.manager.dto.ExpensesDto;
import com.man.ger.manager.entity.CodeMnemonic;
import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.exseption.RangaDateExseption;
import com.man.ger.manager.service.ExpensesService;
import com.man.ger.manager.view.ExpensesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
public class ExpensesController {
    @Autowired
    ExpensesService expensesService;

    @PostMapping("/add")
    public void add(@RequestBody @Valid ExpensesDto expensesDto){
        if(expensesDto.getDate().isAfter(LocalDate.now())){
            throw new RangaDateExseption("date cannot be more real");
        }
        expensesService.addExpenses(expensesDto);
    }

    @GetMapping("/summ")
    public BigDecimal summ(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
                           @RequestParam(required = false)Long categoryId,
                           @RequestParam CodeMnemonic codeMnemonic) throws IOException {
        return  expensesService.sumByMnemonic(from,to,categoryId, codeMnemonic);
    }

    @DeleteMapping("/delete")
    public void delgete(@RequestParam Long id){
        expensesService.delete(id);
    }

    @GetMapping("/getsort")
    public Map<Long, List<Expenses>> getSort(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return expensesService.groupByCategory(date);
    }

    @GetMapping("/getexpeses")
    public List<ExpensesView> getExpensesBySizeAndCategory(@RequestParam Long categoryId,@RequestParam int limit){
        return expensesService.getExpensesBySizeAndCategory(categoryId,limit);
    }


}
