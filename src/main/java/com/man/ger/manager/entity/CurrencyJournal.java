package com.man.ger.manager.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "j_currency")
@Entity
public class CurrencyJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CodeMnemonic mnemonic;
    @CreatedDate
    private LocalDate updateDate;
    private BigDecimal rateSell;


    public CurrencyJournal() {
    }

    public CurrencyJournal(BigDecimal rateSell, CodeMnemonic mnemonic) {
        this.rateSell = rateSell;
        this.mnemonic = mnemonic;
    }
}
