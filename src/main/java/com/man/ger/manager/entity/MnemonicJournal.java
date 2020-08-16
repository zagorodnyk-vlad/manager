package com.man.ger.manager.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "j_mnemonic")
@Entity
public class MnemonicJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mnemonic;
    private int cod;

    public MnemonicJournal() {
    }

    public MnemonicJournal(String mnemonic, int cod) {
        this.mnemonic = mnemonic;
        this.cod = cod;
    }
}
