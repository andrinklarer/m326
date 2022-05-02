package com.google;

import java.util.ArrayList;
import java.util.Iterator;
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

    public void addShares(Share share) {
        shares.add(share);
    }
}
