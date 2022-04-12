package com.google;

public class User {
    private String username;
    private String password;
    private Portfolio portfolio;

    public User() {
    }

    public User(String username, String password, Portfolio portfolio) {
        this.username = username;
        this.password = password;
        this.portfolio = portfolio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
