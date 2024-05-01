package org.example.task5.service;

import java.math.BigDecimal;
import org.example.task5.model.entity.CurrencyPair;

public interface ExchangeRateService {

    void setRate(CurrencyPair currencyPair, BigDecimal amount);

    BigDecimal getExchangeRate(CurrencyPair currencyPair);

    BigDecimal exchange(CurrencyPair currencyPair, BigDecimal amount);

}
