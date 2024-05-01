package org.example.task5.model.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.example.task5.model.entity.CurrencyPair;

public class ExchangeRateDaoImpl implements ExchangeRateDao {

    private final Map<CurrencyPair, BigDecimal> exchangeRates;

    public ExchangeRateDaoImpl() {
        this.exchangeRates = new HashMap<>();
    }

    @Override
    public BigDecimal save(CurrencyPair currencyPair, BigDecimal rate) {
        return exchangeRates.put(currencyPair, rate);
    }

    @Override
    public BigDecimal get(CurrencyPair currencyPair) {
        return exchangeRates.get(currencyPair);
    }

    @Override
    public boolean contains(CurrencyPair currencyPair) {
        return exchangeRates.containsKey(currencyPair);
    }

}
