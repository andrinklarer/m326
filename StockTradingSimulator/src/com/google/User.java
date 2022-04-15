package com.google;

import java.io.Serializable;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public String toString() {
        return "User:: Username=" + this.username + " Password=" + this.password + " Portfolio=" + this.portfolio;
    }
}
