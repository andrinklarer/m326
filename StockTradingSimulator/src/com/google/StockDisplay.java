package com.google;

import javax.swing.*;
import java.awt.*;

public class StockDisplay extends JPanel {
    private Stock stock;
    private Display display;
    private JPanel stats;

    public StockDisplay(Display display, String ticker) {
        this.display = display;
        this.stock = StockMarket.getStockByTicker(ticker);

        this.stats = new JPanel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);

        initStats();

        this.add(stats);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(new DrawGraph(stock, stock.getPriceHistory()));
    }

    private void initStats(){
        stats.setLayout(new GridLayout(2,2));
        stats.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        stats.setMaximumSize(new Dimension(500,100));
        stats.setMinimumSize(new Dimension(500, 100));

        JLabel title = new JLabel(stock.getName(), SwingConstants.CENTER);
        title.setFont(DefaultValues.FONT_TITLE);
        title.setForeground(DefaultValues.COLOR_TEXT_MAIN);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        this.add(title);

        JLabel ticker = new JLabel("Ticker: " + stock.getTicker(), SwingConstants.CENTER);
        ticker.setFont(DefaultValues.FONT_MAIN);
        ticker.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        JLabel volume = new JLabel("Volume: " + stock.getVolume(), SwingConstants.CENTER);
        volume.setFont(DefaultValues.FONT_MAIN);
        volume.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        JLabel available = new JLabel("Available Shares: " + stock.getAvailable(), SwingConstants.CENTER);
        available.setFont(DefaultValues.FONT_MAIN);
        available.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        JLabel currentPrice = new JLabel("Current Price: " + stock.getPriceHistory().get(stock.getPriceHistory().size() -1), SwingConstants.CENTER);
        currentPrice.setFont(DefaultValues.FONT_MAIN);
        currentPrice.setForeground(DefaultValues.COLOR_TEXT_MAIN);

        stats.add(ticker);
        stats.add(currentPrice);
        stats.add(available);
        stats.add(volume);

    }

}
