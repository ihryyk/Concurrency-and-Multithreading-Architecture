package org.example.task5.model.entity;

public record Currency(String code) {

    @Override
    public String toString() {
        return code;
    }

}
