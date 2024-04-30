package org.example.task5.model.entity;

public class CurrencyPair {

    private final Currency from;
    private final Currency to;

    public CurrencyPair(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

}
