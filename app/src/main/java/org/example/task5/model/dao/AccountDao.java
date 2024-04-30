package org.example.task5.model.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.example.task5.model.entity.Account;

public class AccountDao {

    private final String directory;
    private final Gson gson;

    public AccountDao(String directory) {
        this.directory = directory;
        this.gson = new GsonBuilder().create();
    }

    public Account get(String id) {
        Path path = Paths.get(directory, id);
        String content = null;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(content, Account.class);
    }

    public void save(Account account) {
        Path path = Paths.get(directory, account.getId());
        String content = gson.toJson(account);
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
