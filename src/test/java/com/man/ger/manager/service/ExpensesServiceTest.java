package com.man.ger.manager.service;


import com.man.ger.manager.dao.ExpensesRepository;
import com.man.ger.manager.entity.Category;
import com.man.ger.manager.entity.CodeMnemonic;
import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.entity.ExpensesEnumStatus;
import com.man.ger.manager.exseption.RangaDateExseption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExpensesServiceTest {

  @Autowired
  private ExpensesService expensesService;

  @MockBean
  private ExpensesRepository expensesRepository;

  @MockBean
  private CurrencyService currencyService;


  @Test
  public void sumByUAHRangeError() {
    LocalDate from = LocalDate.of(2020, 1, 8);
    LocalDate to = LocalDate.of(2020, 4, 12);
    Assertions.assertThrows(RangaDateExseption.class, () -> {
      expensesService.sumByUAH(from, to, 5L);

    });
  }

  @Test
  public void sumByUAHDateNotNull() {
    LocalDate from = LocalDate.of(2020, 1, 8);
    List<Expenses> expenses = new ArrayList<>();

    Expenses expenses1 = new Expenses();
    expenses1.setId(4L);
    expenses1.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses1.setSum(new BigDecimal("25"));
    expenses.add(expenses1);

    Expenses expenses2 = new Expenses();
    expenses2.setId(5L);
    expenses2.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses2.setSum(new BigDecimal("35"));
    expenses.add(expenses2);

    Mockito.when(expensesRepository.getByDateAndCategory(from, 4l)).thenReturn(expenses);
    BigDecimal sum = expensesService.sumByUAH(from, null, 4l);
    assertEquals(sum, new BigDecimal("60"));
  }

  @Test
  public void sumByUAHDateNull() {
    List<Expenses> expenses = new ArrayList<>();
    Expenses expenses1 = new Expenses();
    expenses1.setId(4L);
    expenses1.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses1.setSum(new BigDecimal("45"));

    expenses.add(expenses1);
    Mockito.when(expensesRepository.getByDate(LocalDate.now())).thenReturn(expenses);

    BigDecimal sum = expensesService.sumByUAH(null, null, null);
    assertEquals(sum, new BigDecimal("45"));
  }

  @Test
  public void sum() {
    List<Expenses> expenses = new ArrayList<>();

    Expenses expenses1 = new Expenses();
    expenses1.setSummary(new BigDecimal("45"));
    expenses1.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses.add(expenses1);

    Expenses expenses2 = new Expenses();
    expenses2.setSummary(new BigDecimal("25"));
    expenses2.setStatus(ExpensesEnumStatus.DELETE);
    expenses.add(expenses2);

    Mockito.when(expensesRepository.findByCategoryId(25L)).thenReturn(expenses);
    BigDecimal sum = expensesService.sum(25L);
    assertEquals(sum, new BigDecimal("25"));
  }

  @Test
  public void sumByMnemonic() throws IOException {
    List<Expenses> expenses = new ArrayList<>();
    Expenses expenses1 = new Expenses();
    expenses1.setId(4L);
    expenses1.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses1.setSum(new BigDecimal("2400"));

    expenses.add(expenses1);
    Mockito.when(expensesRepository.getByDate(LocalDate.now())).thenReturn(expenses);

    Mockito.when(currencyService.getRateSalle(CodeMnemonic.EUR)).thenReturn(new BigDecimal("30"));

    BigDecimal sum = expensesService.sumByMnemonic(null, null, null, CodeMnemonic.EUR);
    assertEquals(sum, new BigDecimal("80"));

  }

  @Test
  public void groupByCategory() {
    List<Expenses> expenses = new ArrayList<>();
    Category category = new Category();
    category.setId(1L);

    Category category2 = new Category();
    category2.setId(2L);

    Category category3 = new Category();
    category3.setId((1L));

    Expenses expenses1 = new Expenses();
    expenses1.setSum(new BigDecimal("22.50"));
    expenses1.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses1.setCategory(category);
    expenses.add(expenses1);

    Expenses expenses2 = new Expenses();
    expenses2.setSummary(new BigDecimal("25"));
    expenses2.setStatus(ExpensesEnumStatus.DELETE);
    expenses2.setCategory(category2);

    expenses.add(expenses2);

    Expenses expenses4 = new Expenses();
    expenses1.setSum(new BigDecimal("23.50"));
    expenses1.setStatus(ExpensesEnumStatus.ACTUAL);
    expenses1.setCategory(category3);
    expenses.add(expenses1);

    Mockito.when(expensesRepository.getByDate(LocalDate.now())).thenReturn(expenses);

    Map<Long, List<Expenses>> map = expensesService.groupByCategory(LocalDate.now());
    assertEquals(map.size(), 2);
  }
}
