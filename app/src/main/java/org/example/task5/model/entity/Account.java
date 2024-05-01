package org.example.task5.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;

@Data
public class Account {

    private final String id;
    private final Map<Currency, BigDecimal> balanceMap;
    @JsonIgnore
    private final ReentrantLock lock;

    public Account(String id) {
        this.id = id;
        this.balanceMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    @JsonCreator
    public Account(@JsonProperty("id") String id,
                   @JsonProperty("balanceMap") Map<Currency, BigDecimal> balanceMap) {
        this.id = id;
        this.balanceMap = balanceMap != null ? balanceMap : new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

}
