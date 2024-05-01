package org.example.task5.model.dao;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.example.task5.exception.AccountException;
import org.example.task5.model.entity.Account;
import org.example.task5.util.FileUtil;
import org.example.task5.util.JsonObjectMapper;

@Slf4j
public class AccountDaoImpl implements AccountDao {

  private final String directory;

  public AccountDaoImpl(String path) {
    this.directory = path;
  }

  @Override
  public Account findById(String id) {
    try {
      log.info("Getting account with id = {} ...", id);
      Path path = Paths.get(directory, id);
      String payload = FileUtil.readFromFile(path);
      Account account = JsonObjectMapper.fromJson(payload, Account.class);
      log.info("Get account with id = {} ...", id);
      return account;
    } catch (IOException e) {
      String message = String.format("Account with id = %s not found", id);
      log.error(message, e);
      throw new AccountException(message);
    }
  }

  @Override
  public void save(Account account) {
    Path path = Paths.get(directory, account.getId());
    try {
      log.info("Saving account with id {} ...", account.getId());
      FileUtil.writeToFile(path, JsonObjectMapper.toJson(account));
      log.info("Saved account with id {} ...", account.getId());
    } catch (IOException e) {
      String message = String.format("Account with id = %s not saved", account.getId());
      log.error(message, e);
      throw new AccountException(message);
    }
  }

}
