package org.example.task5.service;

import java.math.BigDecimal;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.CurrencyPair;

public class CurrencyExchangeService {

    private final AccountService accountService;
    private final ExchangeRateService exchangeRateService;

    public CurrencyExchangeService(AccountService accountService, ExchangeRateService exchangeRateService) {
        this.accountService = accountService;
        this.exchangeRateService = exchangeRateService;
    }

    public void exchange(String accountId, CurrencyPair pair, BigDecimal amount) throws Exception {
        Account account = accountService.getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exists");
        }

        BigDecimal balance = accountService.getBalance(account, pair.getFrom());
        if (balance == null || balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        BigDecimal rate = exchangeRateService.getConversionRate(pair);
        if (rate == null) {
            throw new IllegalArgumentException("No rate available from " + pair.getFrom() + " to " + pair.getTo());
        }

        BigDecimal convertedAmount = amount.multiply(rate);

        accountService.debit(account, pair.getFrom(), amount);
        accountService.credit(account, pair.getTo(), convertedAmount);
    }

}
