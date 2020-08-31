package com.man.ger.manager.service;

import com.man.ger.manager.converter.ExpensesDtoToExpenses;
import com.man.ger.manager.converter.ExpensesToExpensesViev;
import com.man.ger.manager.dao.ExpensesRepository;
import com.man.ger.manager.dto.ExpensesDto;

import com.man.ger.manager.entity.Category;
import com.man.ger.manager.entity.CodeMnemonic;
import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.entity.ExpensesEnumStatus;
import com.man.ger.manager.exseption.RangaDateExseption;
import com.man.ger.manager.view.ExpensesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExpensesService {

  @Autowired
  CategoryService categoryService;
  @Autowired
  private ExpensesDtoToExpenses expensesDtoToExpenses;
  @Autowired
  private ExpensesRepository expensesRepository;
  @Autowired
  private CurrencyService currencyService;
  @Autowired
  private ExpensesToExpensesViev expensesToExpensesViev;

  public void addExpenses(ExpensesDto expensesDto) {
    BigDecimal summary = expensesRepository.getSummByDate(expensesDto.getDate());
    if (summary == null) summary = BigDecimal.ZERO;
    Expenses expenses = expensesDtoToExpenses.convert(expensesDto);
    List<Expenses> expenses1 = expensesRepository.getByDate(expensesDto.getDate());
    BigDecimal average = summary.divide(new BigDecimal(expenses1.size()));

    expenses.setSummary(summary.add(expensesDto.getSum()));
    expenses.setMean(average);
    expensesRepository.save(expenses);
  }

  public BigDecimal sumByUAH(LocalDate from, LocalDate to, Long categoryId) {
    List<Expenses> expenses = new ArrayList<>();
    if (from != null && to != null && categoryId != null) {
      if (from.plusDays(60).isBefore(to)) {
        throw new RangaDateExseption("search range exceeded");
      }
      expenses = expensesRepository.findByIdAndDateBetween(categoryId, from, to);
    }
    if (from != null && to == null && categoryId != null) {
      expenses = expensesRepository.getByDateAndCategory(from, categoryId);
    }
    if (from == null && to == null && categoryId == null) {
      expenses = expensesRepository.getByDate(LocalDate.now());
    }

    return expenses.stream()
        .filter(one -> !ExpensesEnumStatus.DELETE.equals(one.getStatus()))
        .map(one -> one.getSum())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }


  public void delete(Long id) {
    Expenses expenses = expensesRepository.findById(id).get();
    List<Expenses> expenses1 = expensesRepository.getByDate(expenses.getDate());
    BigDecimal summ = BigDecimal.ZERO;
    for (Expenses one : expenses1) {
      if (one.getId() > id && one.getId() != id) {
        summ = summ.add(one.getSum());
        one.setSummary(summ);
      } else if (one.getId() != id) {
        summ = summ.add(one.getSum());
      }
    }
    expensesRepository.saveAll(expenses1);
    expensesRepository.deleteById(ExpensesEnumStatus.DELETE, id);
  }

  public BigDecimal sumByMnemonic(LocalDate from, LocalDate to, Long categoryId, CodeMnemonic mnemonic) throws IOException {
    Integer cod = mnemonic.getCode();
    if (cod == 980) {
      return sumByUAH(from, to, categoryId);
    } else if (cod == 840) {
      BigDecimal usd = sumByUAH(from, to, categoryId).divide(currencyService.getRateSalle(CodeMnemonic.USD));
      return usd;
    } else if (cod == 978) {
      BigDecimal eur = sumByUAH(from, to, categoryId).divide(currencyService.getRateSalle(CodeMnemonic.EUR));
      return eur;
    }
    return null;
  }

  public BigDecimal sum(Long categoryId) {
    List<Expenses> expenses = expensesRepository.findByCategoryId(categoryId);
    return expenses.stream()
        .filter(one -> ExpensesEnumStatus.DELETE.equals(one.getStatus()))
        .map(one -> one.getSummary())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Map<Long, List<Expenses>> groupByCategory(LocalDate date) {
    List<Expenses> expenses = expensesRepository.getByDate(date);

    Map<Long, List<Expenses>> listMap = expenses.stream()
        .collect(Collectors.groupingBy(entry -> entry.getCategory().getId(), Collectors.toList()));

    return listMap;

  }

  public List<ExpensesView> getExpensesBySizeAndCategory(Long categoryId, int limit){
    List<Expenses> expenses = expensesRepository.getExpesesBySizeAndCategory(categoryId,limit);
    return  expenses.stream().map(one->expensesToExpensesViev.convert(one)).collect(Collectors.toList());
  }
}
