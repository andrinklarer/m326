package com.google;

import javax.swing.*;

public class StockDisplay extends JPanel {
    private Stock stock;
    private Display display;

    public StockDisplay(Display display, String ticker) {
        this.display = display;
        stock = StockMarket.getStockByTicker(ticker);
        DrawGraph.createAndShowGui(stock);
    }
}
