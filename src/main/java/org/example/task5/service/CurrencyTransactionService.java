package org.example.task5.service;

import java.math.BigDecimal;
import org.example.task5.model.entity.Account;
import org.example.task5.model.entity.CurrencyPair;

public interface CurrencyTransactionService {

    void performTransaction(Account account, CurrencyPair pair, BigDecimal amount);

}
