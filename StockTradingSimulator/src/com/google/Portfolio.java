package com.google;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private double balance;
    private List<Share> shares;

    public Portfolio() {
        shares = new ArrayList<>();
        balance = 0;
    }

    public Portfolio(double balance, List<Share> shares) {
        this.balance = balance;
        this.shares = shares;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }
}
