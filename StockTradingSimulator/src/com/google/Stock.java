package com.google;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//changed currentPrice to price history to store all price changes for better statistics


public class Stock implements Serializable {
    private String name;
    private String ticker;
    private int volume;
    private int available;
    private List<Double> priceHistory;

    public Stock() {
    }

    public Stock(String name, String ticker, int volume, int available, List<Double> priceHistory) {
        this.name = name;
        this.ticker = ticker;
        this.volume = volume;
        this.available = available;
        this.priceHistory = priceHistory;
    }

    public Stock(String name, String ticker, int volume, int available, Double... priceHistory) {
        this.name = name;
        this.ticker = ticker;
        this.volume = volume;
        this.available = available;
        this.priceHistory = Arrays.stream(priceHistory).toList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<Double> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public void addPriceToHistory(Double price) {
        List<Double> newPriceHistory = new ArrayList<>();
        //write all values of price history to a new list
        for (int i = 0; i < priceHistory.size() - 1; i++) {
            newPriceHistory.add(priceHistory.get(i));
        }
        newPriceHistory.add(price);
        setPriceHistory(newPriceHistory);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }


}
