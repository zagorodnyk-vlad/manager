package com.man.ger.manager.view;

import com.man.ger.manager.entity.ExpensesEnumStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpensesView {
  private Long id;
  private BigDecimal sum;
  private LocalDate date;
  private BigDecimal summary;
  private BigDecimal mean;
  private ExpensesEnumStatus status;
  private  Long categoryId;
}
