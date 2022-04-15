package com.google;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) StockMarket.getStocks().add(new Stock("Google" + i, i * 10.0, i * 1000, i * 100));
        for (int i = 0; i < 10; i++) UserManager.getUsers().add(new User("andy" + i, "andy"+i, null));
        UserManager.save();
        StockMarket.save();

        new Display();
    }
}

// Color:
//  Baby Blue
//  0xBFD7ED
//
//  Blue Grotto
//  0x60A3D9
//
//  Royal Blue
//  0x0074B7
//
//  Navy Blue
//  0x003B73