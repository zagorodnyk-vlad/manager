package com.man.ger.manager.dao;

import com.man.ger.manager.entity.CurrencyJournal;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;

public interface CurrencyJournalRepository extends JpaRepository<CurrencyJournal,Long> {


   CurrencyJournal findByMnemonicAndUpdateDate(String mnemonic, LocalDate updateDate);

}
