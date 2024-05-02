package org.example.task5.service;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.CurrencyPair;

@Slf4j
public class CurrencyTransactionServiceImpl implements CurrencyTransactionService {

    private final AccountServiceImpl accountServiceImpl;
    private final ExchangeRateServiceImpl exchangeRateServiceImpl;

    public CurrencyTransactionServiceImpl(AccountServiceImpl accountServiceImpl,
                                          ExchangeRateServiceImpl exchangeRateServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
        this.exchangeRateServiceImpl = exchangeRateServiceImpl;
    }

    @Override
    public void performTransaction(Account account, CurrencyPair pair, BigDecimal amount) {
        log.info("Exchanging of account currency: accountId ={}, from ={}, to ={}, amount ={} ...",
                account.getId(), pair.from(), pair.to(), amount);
        account.getLock().lock();
        try {
            BigDecimal convertedAmount = exchangeRateServiceImpl.exchange(pair, amount);
            accountServiceImpl.withdraw(account, pair.from(), convertedAmount);
            accountServiceImpl.deposit(account, pair.to(), amount);
        } finally {
            account.getLock().unlock();
        }
        log.info("Exchanged of account currency: accountId ={}, from ={}, to ={}, amount ={}.",
                account.getId(), pair.from(), pair.to(), amount);
    }

}
