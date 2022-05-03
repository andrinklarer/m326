package com.google;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Portfolio implements Serializable {
    private double balance;
    private List<Share> shares;
    private List<Share> sellHistory;

    public Portfolio() {
        shares = new ArrayList<>();
        sellHistory = new ArrayList<>();
        balance = 0;
    }

    public Portfolio(double balance, List<Share> shares) {
        this.balance = balance;
        this.shares = shares;
        this.sellHistory = new ArrayList<>();
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

    public void removeShare(Share soldShare) {

        int toRemove = soldShare.getAmount();
        while (toRemove > 0) {
            for (Iterator<Share> shareIterator = shares.iterator(); shareIterator.hasNext();) {
                if (toRemove > 0) {
                    Share share = shareIterator.next();
                    if (share.getStock().getTicker().equals(soldShare.getStock().getTicker())) {
                        if (share.getAmount() > 0) {
                            share.setAmount(share.getAmount() - 1);
                            toRemove--;
                        }
                        if (share.getAmount() == 0) {
                            shareIterator.remove();
                        }
                    }
                }
                else return;
            }
        }
    }

    public int countSharesOfType(Stock stock){
        int count = 0;
        for (Share share : shares) {
            if (share.getStock().getTicker().equals(stock.getTicker())) {
                count += share.getAmount();
            }
        }
        System.out.println("Number of shares of type : " + count);
        return count;
    }

    public List<Share> getSellHistory() {
        return sellHistory;
    }

    public void setSellHistory(List<Share> sellHistory) {
        this.sellHistory = sellHistory;
    }

    public void addShares(Share share) {
        shares.add(share);
    }
}
