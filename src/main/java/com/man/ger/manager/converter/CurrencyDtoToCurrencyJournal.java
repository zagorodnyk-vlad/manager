package com.man.ger.manager.converter;

import com.man.ger.manager.dto.CurrencyDto;
import com.man.ger.manager.entity.CurrencyJournal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CurrencyDtoToCurrencyJournal implements Converter<CurrencyDto, CurrencyJournal> {
    @Override
    public CurrencyJournal convert(CurrencyDto currencyDto) {
        CurrencyJournal currencyJournal= new CurrencyJournal();
        currencyJournal.setUpdateDate(LocalDate.now());
        currencyJournal.setRateSell(new BigDecimal(currencyDto.getRateSell()));
        return currencyJournal;
    }
}
