package org.example.task5.service;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.example.task5.model.dao.ExchangeRateDaoImpl;
import org.example.task5.model.entity.CurrencyPair;

@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateDaoImpl exchangeRateDaoImpl;

    public ExchangeRateServiceImpl(ExchangeRateDaoImpl exchangeRateDaoImpl) {
        this.exchangeRateDaoImpl = exchangeRateDaoImpl;
    }

    @Override
    public void setRate(CurrencyPair currencyPair, BigDecimal amount) {
        log.info("Setting currency pair: from ={}, to ={}, amount ={} ...", currencyPair.from(), currencyPair.to(), amount);
        exchangeRateDaoImpl.save(currencyPair, amount);
        log.info("Set currency pair: from ={}, to ={}, amount ={}.", currencyPair.from(), currencyPair.to(), amount);
    }

    @Override
    public BigDecimal getExchangeRate(CurrencyPair currencyPair) {
        log.info("Getting exchange rate : from ={}, to ={} ...", currencyPair.from(), currencyPair.to());
        if (!exchangeRateDaoImpl.contains(currencyPair)) {
            throw new IllegalArgumentException("Cannot find conversion rate for provided currency pair");
        }
        BigDecimal exchangeRate = exchangeRateDaoImpl.get(currencyPair);
        log.info("Get exchange rate : from ={}, to ={}.", currencyPair.from(), currencyPair.to());
        return exchangeRate;
    }

    @Override
    public BigDecimal exchange(CurrencyPair currencyPair, BigDecimal amount) {
        log.info("Exchanging : from ={}, to ={}, amount ={} ...", currencyPair.from(), currencyPair.to(), amount);
        BigDecimal conversionRate = getExchangeRate(new CurrencyPair(currencyPair.from(), currencyPair.to()));
        BigDecimal result = amount.multiply(conversionRate);
        log.info("Exchanged : from ={}, to ={}, amount ={}.", currencyPair.from(), currencyPair.to(), amount);
        return result;
    }

}
