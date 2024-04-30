package org.example.task5.model.entity;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final String id;
    private final ConcurrentHashMap<Currency, BigDecimal> balanceMap;
    private final ReentrantLock lock;

    public Account(String id) {
        this.id = id;
        this.balanceMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance(Currency currency) {
        return balanceMap.get(currency);
    }

    public void setBalance(Currency currency, BigDecimal amount) {
        balanceMap.put(currency, balanceMap.getOrDefault(currency, BigDecimal.ZERO).add(amount));
    }

    public void withdraw(Currency currency, BigDecimal amount) throws Exception {
        if (balanceMap.getOrDefault(currency, BigDecimal.ZERO).compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
        balanceMap.put(currency, balanceMap.get(currency).subtract(amount));
    }

    public ReentrantLock getLock() {
        return lock;
    }

}
