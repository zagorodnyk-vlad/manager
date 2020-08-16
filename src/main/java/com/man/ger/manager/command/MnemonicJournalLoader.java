package com.man.ger.manager.command;


import com.man.ger.manager.dao.MnemonicJournalRepository;
import com.man.ger.manager.entity.MnemonicJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class MnemonicJournalLoader implements CommandLineRunner {
    @Autowired
    private MnemonicJournalRepository mnemonicJournalRepository;

    @Override
    public void run(String... args) {
        Long size = mnemonicJournalRepository.countJournal();
        if (size == 0) {
            MnemonicJournal usd = new MnemonicJournal("USD",840);
            MnemonicJournal eur = new MnemonicJournal("EUR",978);
            List<MnemonicJournal> mnemonicJournals= new ArrayList<>();
            mnemonicJournals.add(usd);
            mnemonicJournals.add(eur);
            mnemonicJournalRepository.saveAll(mnemonicJournals);
        }
    }
}
