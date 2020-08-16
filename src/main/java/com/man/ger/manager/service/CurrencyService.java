package com.man.ger.manager.service;

import com.man.ger.manager.converter.CurrencyDtoToCurrencyJournal;
import com.man.ger.manager.dao.CurrencyJournalRepository;
import com.man.ger.manager.dto.CurrencyDto;
import com.man.ger.manager.entity.CodeMnemonic;
import com.man.ger.manager.entity.CurrencyJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyJournalRepository currencyJournalRepository;
    @Autowired
    private CurrencyConnectionService currencyConnectionService;
    @Autowired
    private CurrencyDtoToCurrencyJournal currencyDtoToCurrencyJournak;

    public BigDecimal getRateSalle(CodeMnemonic mnemonic) throws IOException {
        /*
            проверяем если у нас курс валют  с сегоднешней датой
         */
        CurrencyJournal currencyJournal = currencyJournalRepository.findByMnemonicAndUpdateDate(mnemonic.name(), LocalDate.now());
        if (currencyJournal != null) {
            return currencyJournal.getRateSell();
        }
         /*
            если нет куса валют с сегоднешней датой идем в банк и достаем ее
         */
        List<CurrencyDto> currencyDtoList = currencyConnectionService.getCurrency();
         /*
           банк нам возвращает большое кол информациии. Фильтруем и получаем только то что нам надо
            Формат ответа банка:
           [
              {
                "currencyCodeA": 840,
                "currencyCodeB": 980,
                "date": 1552392228,
                "rateSell": 27,
                "rateBuy": 27.2,
                "rateCross": 27.1
              }
            ]
         */
        List<CurrencyDto> currencyDtoListSort = currencyDtoList.stream()
                .filter(one -> one.getCurrencyCodeA() == CodeMnemonic.UAN.getCode() && one.getCurrencyCodeB() == CodeMnemonic.USD.getCode())
                .filter(one -> one.getCurrencyCodeA() == CodeMnemonic.UAN.getCode() && one.getCurrencyCodeB() == CodeMnemonic.EUR.getCode())
                .collect(Collectors.toList());

        /*
           перемапливаем из дто в entity так как в базу сохраняем entity

        */
        List<CurrencyJournal> currencyJournals = currencyDtoList
                .stream()
                .map(one -> currencyDtoToCurrencyJournak.convert(one)).collect(Collectors.toList());
        currencyJournalRepository.saveAll(currencyJournals);
        /*
          Находим валюиту по запрусу пользователя и возвращаем ее
        */
        CurrencyDto currencyDto = currencyDtoListSort.stream()
                .filter(one -> one.getCurrencyCodeA() == mnemonic.getCode())
                .findFirst().get();
        return new BigDecimal(currencyDto.getRateSell());

    }
}