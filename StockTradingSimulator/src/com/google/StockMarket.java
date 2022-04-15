package com.google;

import java.util.ArrayList;
import java.util.List;

public class StockMarket {
    private static List<Stock> stocks = new ArrayList<>();

    public static void loadStocks(){
        stocks = (List<Stock>) Filehandler.readFile(DefaultValues.STOCK_DATA_FILE_PATH);
    }

    public static void save() {
        Filehandler.writeFile(DefaultValues.STOCK_DATA_FILE_PATH, stocks);
    }

    public static Stock getStockByTicker(String ticker) {
        return stocks.stream().filter(stock -> stock.getTicker().equals(ticker)).findFirst().orElse(null);
    }

    public static void update() {

    }

    public static List<Stock> getStocks() {
        return stocks;
    }

    public static void setStocks(List<Stock> stocks) {
        StockMarket.stocks = stocks;
    }
}
