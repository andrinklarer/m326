package com.google;

import javax.swing.*;
import java.awt.*;

public class StockDisplay extends JPanel {
    private Stock stock;
    private Display display;
    private JPanel stats;
    private JPanel orders;
    private JLabel currentPrice;
    private JLabel available;

    public StockDisplay(Display display, String ticker) {
        this.display = display;
        this.stock = StockMarket.getStockByTicker(ticker);

        this.stats = new JPanel();
        this.orders = new JPanel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);

        initStats();
        this.add(orders);
        this.add(stats);

        this.add(Box.createRigidArea(new Dimension(0, 0)));
        this.add(new DrawGraph(stock, this));
        initButtons();
    }
    public void reloadStats(){
        currentPrice.setText("Current Price: " + StockMarket.getStockByTicker(stock.getTicker()).getPriceHistory().get(stock.getPriceHistory().size() -1));
        available.setText("Available: " + StockMarket.getStockByTicker(stock.getTicker()).getAvailable());
        repaint();
    }

    private void initButtons(){
        //add button to show all time chart
        JButton button = new JButton("Show All Time Chart");
        button.addActionListener(e -> DrawGraph.isActive = true);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        button.setAlignmentY(Component.TOP_ALIGNMENT);


        //add button to show current chart
        JButton button2 = new JButton("Show Current Chart");
        button2.addActionListener(e -> DrawGraph.isActive = false);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        button.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 0, 0));
        buttonPanel.add(button);
        buttonPanel.add(button2);
        this.add(buttonPanel);
    }

    private void initStats(){
        orders.setLayout(new GridLayout(1,8,20,0));
        orders.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        orders.setMaximumSize(new Dimension(1000,20));
        orders.setMinimumSize(new Dimension(1000, 20));


        JButton buy = new JButton("Buy");
        buy.addActionListener(e -> buy(1));
        buy.setBackground(Color.GREEN);

        JButton buy10 = new JButton("Buy 10");
        buy10.addActionListener(e -> buy(10));
        buy10.setBackground(Color.GREEN);

        JButton sell = new JButton("Sell");
        sell.setBackground(Color.ORANGE);
        sell.addActionListener(e -> sell(1));

        JButton sellAll = new JButton("Sell All");
        sellAll.setBackground(Color.RED);
        sellAll.addActionListener(e -> sell(UserManager.currentUser.getPortfolio().countSharesOfType(stock)));
        orders.add(sell);
        orders.add(sellAll);
        orders.add(buy10);
        orders.add(buy);


        stats.setLayout(new GridLayout(1,4));
        stats.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        stats.setMaximumSize(new Dimension(1000,20));
        stats.setMinimumSize(new Dimension(1000, 20));

        JLabel ticker = new JLabel("Ticker: " + stock.getTicker(), SwingConstants.CENTER);
        ticker.setFont(DefaultValues.FONT_MAIN);
        ticker.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        JLabel volume = new JLabel("Volume: " + stock.getVolume(), SwingConstants.CENTER);
        volume.setFont(DefaultValues.FONT_MAIN);
        volume.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        available = new JLabel("Available: " + stock.getAvailable(), SwingConstants.CENTER);
        available.setFont(DefaultValues.FONT_MAIN);
        available.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        currentPrice = new JLabel("Current Price: " + stock.getPriceHistory().get(stock.getPriceHistory().size() -1), SwingConstants.CENTER);
        currentPrice.setFont(DefaultValues.FONT_MAIN);
        currentPrice.setForeground(DefaultValues.COLOR_TEXT_MAIN);



        stats.add(ticker);
        stats.add(currentPrice);
        stats.add(available);
        stats.add(volume);

    }
    private void buy(int amount){
        if(StockMarket.getStockByTicker(stock.getTicker()).getAvailable() >= amount && UserManager.currentUser.getPortfolio().getBalance() >= amount * stock.getCurrentPrice()){
            System.out.println("Bought " + amount + " shares of " + stock.getTicker());
            UserManager.currentUser.buy(stock, (int) stock.getCurrentPrice(), amount);
            StockMarket.getStockByTicker(stock.getTicker()).setAvailable(stock.getAvailable() - amount);
        }
    }

    private void sell(int amount){
        if(UserManager.currentUser.getPortfolio().countSharesOfType(stock) >= amount){
            System.out.println("Sold " + amount + " shares of " + stock.getTicker());
            UserManager.currentUser.sell(stock, (int) stock.getCurrentPrice(), amount);
            StockMarket.getStockByTicker(stock.getTicker()).setAvailable(stock.getAvailable() + amount);
        }
    }
}
