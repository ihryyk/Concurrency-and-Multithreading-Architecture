package org.example.task5;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.example.task5.model.dao.AccountDaoImpl;
import org.example.task5.model.dao.ExchangeRateDaoImpl;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.Currency;
import org.example.task5.model.entity.CurrencyPair;
import org.example.task5.service.AccountServiceImpl;
import org.example.task5.service.CurrencyTransactionServiceImpl;
import org.example.task5.service.ExchangeRateServiceImpl;

public class CurrencyExchangeApp {

    public static void main(String[] args) {
        AccountDaoImpl accountDaoImpl = new AccountDaoImpl("./accounts");
        AccountServiceImpl accountServiceImpl = new AccountServiceImpl(accountDaoImpl);

        ExchangeRateDaoImpl exchangeRateDaoImpl = new ExchangeRateDaoImpl();
        ExchangeRateServiceImpl exchangeRateServiceImpl = new ExchangeRateServiceImpl(exchangeRateDaoImpl);

        CurrencyTransactionServiceImpl currencyTransactionServiceImpl = new CurrencyTransactionServiceImpl(
                accountServiceImpl,
                exchangeRateServiceImpl);

        Currency usd = new Currency("USD");
        Currency eur = new Currency("EUR");

        exchangeRateServiceImpl.setRate(new CurrencyPair(usd, eur), new BigDecimal("0.85"));

        Account account = new Account("123");
        accountServiceImpl.saveAccount(account);
        accountServiceImpl.deposit(account, usd, new BigDecimal("100"));

        CurrencyPair currencyPair = new CurrencyPair(usd, eur);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> threadTask(currencyTransactionServiceImpl, account, currencyPair));
        }
        shutdownExecutorService(executorService);
    }

    private static void threadTask(CurrencyTransactionServiceImpl transactionService, Account account,
                                   CurrencyPair pair) {
        transactionService.performTransaction(account, pair, new BigDecimal(10));
    }

    private static void shutdownExecutorService(
            ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
