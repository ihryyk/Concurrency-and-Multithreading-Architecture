package org.example.task5.model.dao;

import org.example.task5.model.entity.Account;

public interface AccountDao {

    Account findById(String id);

    void save(Account account);

}
