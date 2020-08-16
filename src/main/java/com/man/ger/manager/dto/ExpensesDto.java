package com.man.ger.manager.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpensesDto {
    @NotNull
    private BigDecimal sum;
    private LocalDate date=LocalDate.now();
    @NotNull
    private Long categoryId;
}
