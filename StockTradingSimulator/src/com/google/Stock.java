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
        this.priceHistory = new ArrayList<>(Arrays.stream(priceHistory).toList());
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<Double> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public void addPriceToHistory(Double price) {
        //write all values of price history to a new list
        List<Double> newPriceHistory = new ArrayList<>(priceHistory);
        newPriceHistory.add(price);
        setPriceHistory(newPriceHistory);
    }

    public double getCurrentPrice() {
        return priceHistory.get(priceHistory.size() - 1);
    }

    public int getVolume() {
        return volume;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }


}
