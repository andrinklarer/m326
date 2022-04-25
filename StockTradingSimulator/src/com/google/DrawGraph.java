package com.google;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class DrawGraph extends JPanel {
    private static int MAX_SCORE = 1000;
    private static final int MIN_SCORE = 0;
    private static int MAX_Y = 1000;
    private static int MIN_Y = 0;
    private static int AVG_SCORE = (MAX_SCORE + MIN_SCORE) / 2;
    private static final int MAX_Y_DATA_POINTS = 20;
    private static final int PREF_W = 800;
    private static final int PREF_H = 650;
    private static final int BORDER_GAP = 50;
    private static final Color GRAPH_COLOR = Color.green;
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_WIDTH = 12;
    private static final int timeout = 1000;
    private static final int MAX_X_DATA_POINTS = 20;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static boolean isActive = false;

    private static List<Double> usedChartScores = new ArrayList<>();
    private static List<Double> allTimeChartScores = new ArrayList<>();
    private static Stock stock;

    public DrawGraph(Stock stock, List<Double> scores) {
        this.stock = stock;
        allTimeChartScores = scores;
        usedChartScores = new ArrayList<>(allTimeChartScores);
    }

    public static void createAndShowGui(Stock stock, int currentPrice) {

        initialChart(currentPrice);
        DrawGraph mainPanel = new DrawGraph(stock, allTimeChartScores);


        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static void initialChart(double currentPrice) {
        MAX_SCORE = (int) currentPrice;
        MAX_Y = (int) currentPrice;
        allTimeChartScores.add(0.0);
        for (int i = 0; i < MAX_X_DATA_POINTS; i++) {
            if (i == MAX_X_DATA_POINTS - 1) {
                allTimeChartScores.add(currentPrice);
            } else {
                int upperBound = (int) ((currentPrice / MAX_X_DATA_POINTS * i) + currentPrice / 10);
                int lowerBound = Math.max((int) ((currentPrice / MAX_X_DATA_POINTS * i) - currentPrice / 10), 0);
                allTimeChartScores.add(Math.random() * (upperBound - lowerBound) + lowerBound);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //lastScore
        double lastScore = allTimeChartScores.get(allTimeChartScores.size() - 1);

        //add button to print the current price
        JButton printButton = new JButton("Print");
        printButton.addActionListener(ef -> System.out.println("\nbought at = " + allTimeChartScores.get(allTimeChartScores.size() - 3)));
        add(printButton);


        //add button to show all time chart
        JButton button = new JButton("Show All Time Chart");
        button.addActionListener(e -> DrawGraph.isActive = true);
        add(button);

        //add button to show current chart
        JButton button2 = new JButton("Show Current Chart");
        button2.addActionListener(e -> DrawGraph.isActive = false);
        add(button2);

        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (usedChartScores.size() - 1);
        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_Y - MIN_Y);

        List<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < usedChartScores.size(); i++) {
            int x1 = (int) (i * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_Y - usedChartScores.get(i)) * yScale + BORDER_GAP);
            graphPoints.add(new Point(x1, y1));
            if (i == usedChartScores.size() - 1) {
                g2.drawString(df.format(usedChartScores.get(i)), x1, y1);
            }
        }
        // create x and y axes
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

        // create hatch marks for y axis.
        for (int i = 0; i < MAX_Y_DATA_POINTS + 1; i++) {
            int x0 = BORDER_GAP;
            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
            int y0 = (getHeight() - (((i) * (getHeight() - BORDER_GAP * 2)) / 20 + BORDER_GAP));
            int y1 = y0;
            g2.drawString(String.valueOf(df.format(((double) (MAX_Y - MIN_Y) / MAX_Y_DATA_POINTS) * (i) + MIN_Y)), x0 - 40, y0 + 3);
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < Math.min(usedChartScores.size() - 1, 100); i++) {
            int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / Math.min(usedChartScores.size() - 1, 100) + BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1);
            if (i == Math.min(usedChartScores.size() - 2, 99)) {
                g2.drawString("now", x0 - 10, y0 + 15);
            }
        }

        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
        if (checkCrash()) {
            g2.setColor(Color.red);
            g2.drawString("CRASHED", getWidth() / 2 - 50, getHeight() / 2);
            ;
        } else {
            updateChart();
        }
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static double semiRandomNumber() {
        double lastScore = allTimeChartScores.get(allTimeChartScores.size() - 1);
        if (lastScore == MIN_SCORE) {
            return MIN_SCORE;
        }
        System.out.println("lastScore = " + lastScore);
        //rare chance to crash
        if ((int) (Math.random() * 2000) == 100) {
            return MIN_SCORE;
        }
        double diff = lastScore / 100 * 10;
        ;
        double lowerBound;
        double upperBound;


        if (lastScore <= MIN_SCORE + (MAX_SCORE / 20.0)) {
            //chance to completely crash if prices are in bottom 5%
            int random = (int) (Math.random() * 10) / 2;
            if (random == 0) {
                System.out.println("CRASHED cause low");
                return MIN_SCORE;
            }
        }

        if (lastScore > AVG_SCORE) {
            lowerBound = lastScore - diff;
            upperBound = lastScore + diff;
        } else if (lastScore < AVG_SCORE) {
            lowerBound = lastScore - diff;
            upperBound = lastScore + diff * 1.2;
        } else {
            lowerBound = lastScore - diff;
            upperBound = lastScore + diff;
        }

        return (Math.random() * (upperBound - lowerBound) + lowerBound);
    }

    public boolean checkCrash() {
        double lastScore = allTimeChartScores.get(allTimeChartScores.size() - 1);
        return lastScore == 0;
    }

    private void updateChart() {
        if (isActive) {
            updateAllTimeChart();
        } else {
            updateSmallChart();
        }
    }

    private void updateAllTimeChart() {
        timeout();
        usedChartScores.clear();
        usedChartScores.addAll(allTimeChartScores);
        updateList();
        updateChartBounds();
        repaint();
    }

    private void updateSmallChart() {
        timeout();
        usedChartScores.clear();
        for (int i = 0; i < (Math.min(20, allTimeChartScores.size())); i++) {
            usedChartScores.add(i, allTimeChartScores.get(allTimeChartScores.size() - (Math.min(20, allTimeChartScores.size())) + i));
        }

        if (usedChartScores.size() > MAX_X_DATA_POINTS) {
            usedChartScores.remove(0);
        }
        updateList();
        updateChartBounds();
        repaint();
    }

    private void updateList() {
        allTimeChartScores.add(Double.valueOf(df.format(semiRandomNumber())));
        usedChartScores.add(allTimeChartScores.get(allTimeChartScores.size() - 1));
        StockMarket.getStockByTicker(stock.getTicker()).addPriceToHistory(allTimeChartScores.get(allTimeChartScores.size() - 1));
       // StockMarket.getStockByTicker("AAPL").getPriceHistory().add(usedChartScores.get(usedChartScores.size() - 1));
    }

    private void updateChartBounds() {
        setMaxScore();
        int avg = getRecentAverage();
        double max = Collections.max(usedChartScores, null);
        double min = Collections.min(usedChartScores, null);
        setMaxY((int) (Math.max((1.5 * avg), Math.max(max, 10))));
        setMinY((int) (Math.min((avg / 2.0), min)));
        setAvgScore(avg);
    }

    public void setMaxScore() {
        MAX_SCORE = (int) (double) Collections.max(usedChartScores, null);
    }

    private int getRecentAverage() {
        double sum = 0;
        for (int i = 0; i < Math.min(15, usedChartScores.size()); i++) {
            sum += usedChartScores.get(i);
        }
        return (int) (sum / Math.min(15, usedChartScores.size()));
    }

    private void timeout() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setMaxY(int maxScore) {
        MAX_Y = maxScore;
    }

    public static void setMinY(int minScore) {
        MIN_Y = minScore;
    }

    public static void setAvgScore(int avgScore) {
        AVG_SCORE = avgScore;
    }

}