package com.google;

import java.time.LocalDateTime;

public class Share {
    private Stock stock;
    private double buyPrice;
    private LocalDateTime timeStamp;
    private int amount;

    public Share() {
    }

    public Share(Stock stock, double buyPrice, LocalDateTime timeStamp, int amount) {
        this.stock = stock;
        this.buyPrice = buyPrice;
        this.timeStamp = timeStamp;
        this.amount = amount;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
