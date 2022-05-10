package com.google;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class StockMarket {
    private static List<Stock> stocks = new ArrayList<>();
    private static List<StockUpdateObserver> itemList = new ArrayList<>();
    private static Timer timer;
    private static final int TIMER_DELAY = 1000;

    /**
     * This method will load the data from the saved file
     */
    public static void loadStocks() {
        stocks = (List<Stock>) Filehandler.readFile(DefaultValues.STOCK_DATA_FILE_PATH);
    }

    /**
     * This method will start the timer, which updates the stocks
     */
    public static void startMarket() {
        timer = new Timer(TIMER_DELAY, new StockMarketUpdate());
        timer.start();
    }

    /**
     * This method will save the stocks into a file
     */
    public static void save() {
        Filehandler.writeFile(DefaultValues.STOCK_DATA_FILE_PATH, stocks);
    }

    public static Stock getStockByTicker(String ticker) {
        return stocks.stream().filter(stock -> stock.getTicker().equals(ticker)).findFirst().orElse(null);
    }

    public static Stock getStockByName(String name) {
        return stocks.stream().filter(stock -> stock.getName().equals(name)).findFirst().orElse(null);
    }

    public static List<Stock> getStocks() {
        return stocks;
    }

    public static List<StockUpdateObserver> getItemList() {
        return itemList;
    }

    /**
     * This class is used to update the stocks
     */
    public static class StockMarketUpdate implements ActionListener {
        /**
         * This method will be called from the timer to update the stocks and call the list elements
         * @param e the timer event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Stock stock:stocks) if (stock.getCurrentPrice() != 0) stock.addPriceToHistory(semiRandomNumber(stock.getPriceHistory()));
            for (StockUpdateObserver observer: itemList) observer.update(stocks);
        }

        /**
         * This method will generate a new value of the stock
         * @param allTimeChartScores the history of the stock value
         * @return the newly generated value
         */
        public double semiRandomNumber(List<Double> allTimeChartScores) {
            double lastScore = allTimeChartScores.get(allTimeChartScores.size() - 1);
            if (lastScore == DefaultValues.MIN_SCORE) {
                return DefaultValues.MIN_SCORE;
            }
            //rare chance to crash

            if ((int) (Math.random() * 5000) == 100) {
                return DefaultValues.MIN_SCORE;
            }
            if (lastScore <= allTimeChartScores.stream().mapToDouble(value -> value)
                    .max().orElse(DefaultValues.MIN_SCORE) / 20.0) {
                //chance to completely crash if prices are in bottom 5%
                int random = (int) (Math.random() * 10) / 2;
                if (random == 0) {
                    return DefaultValues.MIN_SCORE;
                }
            }

            //10 % from last score
            double diff = lastScore / 10;
            double lowerBound = lastScore - diff;
            double upperBound = lastScore + diff *
                    (lastScore < allTimeChartScores.stream().mapToDouble(value -> value)
                            .average().orElse(DefaultValues.MIN_SCORE) ? 1.2 : 1);

            return (Math.random() * (upperBound - lowerBound) + lowerBound);
        }
    }
}

