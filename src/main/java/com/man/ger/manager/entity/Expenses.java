package com.man.ger.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal sum;
    @OneToOne
    private Category category;
    private LocalDate date;
    private BigDecimal summary;
    private BigDecimal mean;
    @Enumerated(EnumType.STRING)
    private ExpensesEnumStatus status;

    public Expenses() {
    }

    public Expenses(BigDecimal sum, Category category, LocalDate date) {
        this.sum = sum;
        this.category = category;
        this.date = date;
    }

}
