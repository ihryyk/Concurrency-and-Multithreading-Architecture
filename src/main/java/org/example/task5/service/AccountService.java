package org.example.task5.service;

import java.math.BigDecimal;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.Currency;

public interface AccountService {

    Account getAccount(String accountId);

    void saveAccount(Account account);

    void withdraw(Account account, Currency currency, BigDecimal amount);

    void deposit(Account account, Currency currency, BigDecimal amount);

    BigDecimal getBalanceByCurrency(Account account, Currency currency);

}
