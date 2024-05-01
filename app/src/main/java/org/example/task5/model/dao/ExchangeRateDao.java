package org.example.task5.model.dao;

import java.math.BigDecimal;
import org.example.task5.model.entity.CurrencyPair;

public interface ExchangeRateDao {

    BigDecimal save(CurrencyPair currencyPair, BigDecimal rate);

    BigDecimal get(CurrencyPair currencyPair);

    boolean contains(CurrencyPair currencyPair);

}
