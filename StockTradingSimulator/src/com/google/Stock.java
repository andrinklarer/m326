package com.google;

public class Stock {
    private String ticker;
    private double currentPrice;
    private int volume;
    private int available;

    public Stock() {
    }

    public Stock(String ticker, double currentPrice, int volume, int available) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.volume = volume;
        this.available = available;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
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
