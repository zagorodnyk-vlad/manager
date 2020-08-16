package com.man.ger.manager.dao;

import com.man.ger.manager.entity.MnemonicJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MnemonicJournalRepository extends JpaRepository<MnemonicJournal,Long> {

    @Query("select mne from MnemonicJournal mne where mne.mnemonic=:mnemonic")
    MnemonicJournal findByMnemonic(@Param("mnemonic")String Mnemonic);

    @Query("select count(id) from MnemonicJournal")
    Long countJournal();
}
