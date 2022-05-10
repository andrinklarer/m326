package com.google;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StockGraph extends JPanel implements StockUpdateObserver {
    private static final int BORDER_GAP = 60;
    private static final int Y_BORDER_GAP = 30;
    private static final int MAX_AMOUNT_HATCHES = 20;
    private static final int GRAPH_POINT_WIDTH = 12;
    private static final int MAX_SHORT_LIST_LENGTH = 20;

    private static final Color GRAPH_COLOR = Color.green;
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);

    private String ticker;
    private static boolean isAllTime = false;
    private int MAX_Y = 1000;
    private int MIN_Y = 0;

    private static List<Double> usedChartScores = new ArrayList<>();

    public StockGraph(String ticker) {
        this.ticker = ticker;
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);

        StockMarket.getItemList().add(this);
        update(StockMarket.getStocks());
    }

    /**
     * This method will draw the panel
     * @param g the graphics of the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawHitches(g2);
        drawGraph(g2);
    }

    /**
     * This method updates the stock list and reload the panel
     * @param stocks the new list of stocks
     */
    @Override
    public void update(List<Stock> stocks) {
        if (isAllTime) {
            stocks.stream().filter(stock -> stock.getTicker().equals(ticker)).findFirst().ifPresent(currentStock -> usedChartScores = currentStock.getPriceHistory());
        } else {
            stocks.stream().filter(stock -> stock.getTicker().equals(ticker)).findFirst().ifPresent(currentStock -> {
                usedChartScores.clear();
                int length = Math.min(MAX_SHORT_LIST_LENGTH, currentStock.getPriceHistory().size());
                for (int i = 0; i < length; i++) {
                    usedChartScores.add(currentStock.getPriceHistory().get(currentStock.getPriceHistory().size() - length + i));
                }
            });
        }
        if (isAllTime) {
            MIN_Y = 0;
        }else {
            MIN_Y = (int) Math.min(
                    (int) usedChartScores.stream().mapToDouble(value -> value).average().orElse(0) / 2.0,
                    (int) usedChartScores.stream().mapToDouble(value -> value).min().orElse(0));
        }MAX_Y = (int) (usedChartScores.stream().mapToDouble(value -> value).max().orElse(1000));
        if (this.getGraphics() != null) paintComponent(this.getGraphics());
    }

    /**
     * This method will draw the hitches to the panel
     * @param g2 the graphics of the panel
     */
    private void drawHitches(Graphics2D g2) {
        // create x and y axes
        g2.drawLine(BORDER_GAP, getHeight() - Y_BORDER_GAP, BORDER_GAP, Y_BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - Y_BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - Y_BORDER_GAP);

        // create hatch marks for y axis.
        for (int i = 0; i < MAX_AMOUNT_HATCHES + 1; i++) {
            int x0 = BORDER_GAP;
            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
            int y0 = (getHeight() - (((i) * (getHeight() - Y_BORDER_GAP * 2)) / 20 + Y_BORDER_GAP));
            int y1 = y0;
            String hatchData = String.valueOf(DefaultValues.DECIMAL_FORMAT.format(((double) (MAX_Y - MIN_Y) / MAX_AMOUNT_HATCHES) * (i) + MIN_Y));
            g2.drawString(hatchData, x0 - hatchData.length() * 7, y0 + 3);
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < Math.min(usedChartScores.size() - 1, 100); i++) {
            int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / Math.min(usedChartScores.size() - 1, 100) + BORDER_GAP;
            int x1 = x0;
            int y0 = getHeight() - Y_BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0, x1, y1);
            if (i == Math.min(usedChartScores.size() - 2, 99)) {
                g2.drawString("now", x0 - 10, y0 + 15);
            }
        }
    }

    /**
     * The method will draw the graph to the panel
     * @param g2 the graphics of the panel
     */
    private void drawGraph(Graphics2D g2) {
        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (usedChartScores.size() - 1);
        double yScale = ((double) getHeight() - 2 * Y_BORDER_GAP) / (MAX_Y - MIN_Y);

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < usedChartScores.size(); i++) {
            int x1 = (int) (i * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_Y - usedChartScores.get(i)) * yScale + Y_BORDER_GAP);
            graphPoints.add(new Point(x1, y1));
            if (i == usedChartScores.size() - 1) {
                g2.drawString(DefaultValues.DECIMAL_FORMAT.format(usedChartScores.get(i)), x1, y1);
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
    }

    public static boolean isIsAllTime() {
        return isAllTime;
    }

    public static void setIsAllTime(boolean isAllTime) {
        StockGraph.isAllTime = isAllTime;
    }
}
