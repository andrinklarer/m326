package com.google;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Apple", "AAPL", 100, 100000, 1000));
        stocks.add(new Stock("Google", "GOOG", 200, 200000, 2000));
        stocks.add(new Stock("Microsoft", "MSFT", 300, 300000, 3000));
        stocks.add(new Stock("Facebook", "FB", 400, 400000, 4000));
        stocks.add(new Stock("Amazon", "AMZN", 500, 500000, 5000));
        stocks.add(new Stock("Intel", "INTC", 600, 600000, 6000));
        stocks.add(new Stock("Oracle", "ORCL", 700, 700000, 7000));
        stocks.add(new Stock("IBM", "IBM", 800, 800000, 8000));
        stocks.add(new Stock("Alphabet", "GOOGL", 900, 900000, 9000));
        stocks.add(new Stock("Yahoo", "YHOO", 1000, 1000000, 10000));



        StockMarket.getStocks().addAll(stocks);
        for (int i = 0; i < 10; i++) UserManager.getUsers().add(new User("andy" + i, "andy"+i, null));
        UserManager.save();
        StockMarket.save();

        new Display();
    }
}

// Color:
//  Baby Blue
//  0xBFD7ED
//
//  Blue Grotto
//  0x60A3D9
//
//  Royal Blue
//  0x0074B7
//
//  Navy Blue
//  0x003B73