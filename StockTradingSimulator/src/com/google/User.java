package com.google;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class User implements Serializable {
    private String username;
    private Integer password;
    private Portfolio portfolio;

    public User() {
    }

    public User(String username, Integer password, Portfolio portfolio) {
        this.username = username;
        this.password = password;
        this.portfolio = portfolio;
    }

    public User(String username, String password, Portfolio portfolio) {
        this(username, password.hashCode(), portfolio);
    }

    public String getUsername() {
        return username;
    }

    public Integer getPassword() {
        return password;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Adds the stock to the profile and removes the price of the stock from the balance
     * @param stock the stock to add
     * @param currentPrice the price of the bought stock
     * @param quantity the amount of stocks
     */
    public void buy(Stock stock, int currentPrice, int quantity) {
        portfolio.setBalance(portfolio.getBalance() - (currentPrice * quantity));
        portfolio.addShares(new Share(stock, currentPrice, LocalDateTime.now(),quantity));

    }

    /**
     * This method will sell the stock and add the price to the balance and adds the sold share to the sell history
     * @param stock the sold stock
     * @param currentPrice the price of the stock
     * @param quantity the amount of stocks sold
     */
    public void sell(Stock stock, int currentPrice, int quantity) {
        Share shareToSell = new Share(stock, currentPrice, LocalDateTime.now(), quantity);
        portfolio.setBalance(portfolio.getBalance() + (currentPrice * quantity));
        portfolio.removeShare(shareToSell);
        updateSellHistory(shareToSell);
    }

    /**
     * This method will add a stock to the sell history
     * @param share the share to add
     */
    private void updateSellHistory(Share share) {
        portfolio.getSellHistory().add(share);
    }


    @Override
    public String toString() {
        return "User:: Username=" + this.username + " Password=" + this.password + " Portfolio=" + this.portfolio;
    }
}
