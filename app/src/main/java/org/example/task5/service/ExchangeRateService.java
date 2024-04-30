package org.example.task5.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.example.task5.model.entity.Currency;
import org.example.task5.model.entity.CurrencyPair;

public class ExchangeRateService {

    private final Map<CurrencyPair, BigDecimal> exchangeRates;

    public ExchangeRateService() {
        this.exchangeRates = new HashMap<>();
    }

    public void setRate(CurrencyPair currencyPair, BigDecimal amount) {
        exchangeRates.put(currencyPair, amount);
    }


    public BigDecimal getConversionRate(CurrencyPair currencyPair) {
        if (exchangeRates.containsKey(currencyPair)) {
            return exchangeRates.get(currencyPair);
        } else {
            throw new IllegalArgumentException("Cannot find conversion rate for provided currency pair");
        }
    }

    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        BigDecimal conversionRate = getConversionRate(new CurrencyPair(from, to));
        return amount.multiply(conversionRate);
    }

}
