package com.man.ger.manager.converter;

import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.view.ExpensesView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpensesToExpensesViev implements Converter<Expenses, ExpensesView> {

  @Override
  public ExpensesView convert(Expenses expenses) {
    ExpensesView expenses1 = new ExpensesView();
    expenses1.setSum(expenses.getSum());
    expenses1.setCategoryId(expenses.getCategory().getId());
    expenses1.setStatus(expenses.getStatus());
    expenses1.setSummary(expenses.getSummary());
    expenses1.setId(expenses.getId());
    expenses1.setDate(expenses.getDate());
    expenses1.setMean(expenses.getMean());
    return expenses1;
  }
}
