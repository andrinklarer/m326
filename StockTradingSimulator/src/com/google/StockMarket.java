package com.google;

import java.util.ArrayList;
import java.util.List;

public class StockMarket {
    private static List<Stock> stocks = new ArrayList<>();

    public static void update() {

    }

    public static List<Stock> getStocks() {
        return stocks;
    }

    public static void setStocks(List<Stock> stocks) {
        StockMarket.stocks = stocks;
    }
}
