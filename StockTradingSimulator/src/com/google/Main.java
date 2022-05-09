package com.google;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Initialize some users and stocks - comment this out to use the data from the file
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Apple", "AAPL", 100000, 1000, 100.0, 120.0));
        stocks.add(new Stock("Google", "GOOG", 200000, 2000, 300.0, 200.0));
        stocks.add(new Stock("Microsoft", "MSFT", 300000, 3000, 300.0));
        stocks.add(new Stock("Facebook", "FB", 400000, 4000, 400.0));
        stocks.add(new Stock("Amazon", "AMZN", 500000, 5000, 500.0));
        stocks.add(new Stock("Intel", "INTC", 600000, 6000, 600.0));
        stocks.add(new Stock("Oracle", "ORCL", 700000, 7000, 700.0));
        stocks.add(new Stock("IBM", "IBM", 800000, 8000, 800.0));
        stocks.add(new Stock("Alphabet", "GOOGL", 900000, 9000, 900.0));
        stocks.add(new Stock("Yahoo", "YHOO", 1000000, 10000, 1000.0));

        StockMarket.getStocks().addAll(stocks);
        for (int i = 0; i < 10; i++) UserManager.getUsers().add(new User("andy" + i, "andy" + i, new Portfolio(10000 * i, new ArrayList<>())));
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