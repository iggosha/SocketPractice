package com.tcp;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductPack implements Serializable {
    int amount;
    LocalDateTime timeOfCreation;

    @Override
    public String toString() {
        return "УпаковкаТоваров{" +
                "Количество=" + amount +
                ", ВремяИзготовления=" + timeOfCreation +
                '}';
    }

    public ProductPack() {
        amount = (int) (Math.random() * 10) + 1;
        timeOfCreation = LocalDateTime.now();
    }
}
