package org.example.task5.service;

import java.math.BigDecimal;
import org.example.task5.model.dao.AccountDao;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.Currency;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(String accountId) {
        return accountDao.get(accountId);
    }

    public void saveAccount(Account account) {
        accountDao.save(account);
    }

    public BigDecimal getBalance(Account account, Currency currency) {
        return account.getBalance(currency);
    }

    public void credit(Account account, Currency currency, BigDecimal amount) {
        account.getLock().lock();
        try {
            BigDecimal balance = account.getBalance(currency);
            if (balance == null) {
                balance = BigDecimal.ZERO;
            }
            balance = balance.add(amount);
            account.setBalance(currency, balance);
            accountDao.save(account);
        } finally {
            account.getLock().unlock();
        }
    }

    public void debit(Account account, Currency currency, BigDecimal amount) throws Exception {
        account.getLock().lock();
        try {
            BigDecimal balance = account.getBalance(currency);
            if (balance == null || balance.compareTo(amount) < 0) {
                throw new Exception("Insufficient funds");
            }
            balance = balance.subtract(amount);
            account.setBalance(currency, balance);
            accountDao.save(account);
        } finally {
            account.getLock().unlock();
        }
    }

}
