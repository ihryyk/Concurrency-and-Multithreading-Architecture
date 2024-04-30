package org.example.task5;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.Currency;
import org.example.task5.model.entity.CurrencyPair;
import org.example.task5.service.AccountService;
import org.example.task5.service.CurrencyExchangeService;
import org.example.task5.service.ExchangeRateService;

public class CurrencyExchangeApp {

    private static final Logger LOGGER = Logger.getLogger(CurrencyExchangeApp.class.getName());

    public static void main(String[] args) {
        // Create services
        //     AccountDao accountDao = new FileAccountDao("path/to/your/directory");
        AccountService accountService = new AccountService(accountDao);
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(accountService,
                exchangeRateService);

        // Create currencies
        Currency usd = new Currency("USD");
        Currency eur = new Currency("EUR");

        // Set exchange rate
        exchangeRateService.setRate(new CurrencyPair(usd, eur), new BigDecimal("0.85"));

        // Create account and set initial balance
        Account account = new Account("123");
        accountService.saveAccount(account);
        accountService.credit(account, usd, new BigDecimal("100"));

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Submit tasks
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    currencyExchangeService.exchange(account.getId(), usd, eur, new BigDecimal(10));
                    LOGGER.info("Exchange successful");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Failed to perform exchange operation", e);
                }
            });
        }

        // Shutdown the executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
