package org.example.task5.service;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.example.task5.exception.AccountException;
import org.example.task5.model.dao.AccountDaoImpl;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.Currency;

@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountDaoImpl accountDaoImpl;

    public AccountServiceImpl(AccountDaoImpl accountDaoImpl) {
        this.accountDaoImpl = accountDaoImpl;
    }

    @Override
    public Account getAccount(String accountId) {
        log.info("Getting account:  accountId ={} ...", accountId);
        Account account = accountDaoImpl.findById(accountId);
        log.info("Get account:  accountId ={} ...", accountId);
        return account;
    }

    @Override
    public void saveAccount(Account account) {
        log.info("Saving account:  accountId ={} ...", account.getId());
        accountDaoImpl.save(account);
        log.info("Saved account:  accountId ={} ...", account.getId());
    }

    @Override
    public void withdraw(Account account, Currency currency, BigDecimal amount) {
        log.info("Withdrawal of funds from the account balance: accountId: {}, currency: {}, amount: {} ...",
                account.getId(), currency.code(), amount);
        account.getLock().lock();
        try {
            BigDecimal balance = getBalanceByCurrency(account, currency);
            checkBalance(account, currency, balance, amount);
            balance = balance.subtract(amount);
            updateBalance(account, currency, balance);
            log.info("Funds withdrawn from the account balance: accountId: {}, currency: {}, amount: {}.",
                    account.getId(), currency.code(), amount);
        } finally {
            account.getLock().unlock();
        }
    }

    @Override
    public void deposit(Account account, Currency currency, BigDecimal amount) {
        log.info("Depositing funds to the account: accountId ={}, currency ={}, amount ={} ...",
                account.getId(), currency.code(), amount);
        BigDecimal balance = getBalanceByCurrency(account, currency);
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        balance = balance.add(amount);
        updateBalance(account, currency, balance);
        log.info("Deposited funds to the account: accountId ={}, currency ={}, amount ={}, balance ={}.",
                account.getId(), currency.code(), amount, balance);
    }

    private void checkBalance(Account account, Currency currency, BigDecimal balance, BigDecimal amount) {
        if (balance == null || balance.compareTo(amount) < 0) {
            String message = String.format(
                    "Insufficient funds for the operation: accountId = %s, currency = %s, amount = %s",
                    account.getId(), currency.code(), amount);
            log.error(message);
            throw new AccountException(message);
        }
    }

    @Override
    public BigDecimal getBalanceByCurrency(Account account, Currency currency) {
        log.info("Receiving the account balance: account ={}, currency ={} ...",
                account.getId(), currency.code());
        BigDecimal balance = account.getBalanceMap().get(currency);
        log.info("Received the account balance: account ={}, currency ={}, balance ={}.",
                account.getId(), currency.code(), balance);
        return balance;
    }

    private void updateBalance(Account account, Currency currency, BigDecimal amount) {
        log.info("Updating the account balance: account ={}, currency ={}, amount ={} ...",
                account.getId(), currency.code(), amount);
        account.getBalanceMap().put(currency, amount);
        accountDaoImpl.save(account);
        log.info("Updated the account balance: account ={}, currency ={}, amount ={}.",
                account.getId(), currency.code(), amount);
    }

}
