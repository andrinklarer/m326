package com.google;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) StockMarket.getStocks().add(new Stock("Google" + i, i * 10.0, i * 1000, i * 100));

        new Display();
    }
}

// Color:
// rgb(8, 176, 250)
// rgb(11, 99, 222)
// rgb(0, 33, 245)
// rgb(44, 11, 222)
// rgb(122, 13, 255)