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

    /**
     * Method to remove the right amount of shares from the portfolio
     * @param soldShare the share to sell
     */
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

    /**
     * Method to get the amount of shares of a certain stock
     * @param stock the stock to check
     * @return amount of shares a user owns
     */
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

    /**
     * This method adds a share to current share list
     * @param share the share to add
     */
    public void addShares(Share share) {
        shares.add(share);
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

    public List<Share> getSellHistory() {
        return sellHistory;
    }
}
