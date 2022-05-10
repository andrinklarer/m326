package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StockOverviewDisplay extends JPanel implements StockUpdateObserver {
    private JLabel label;
    private Display display;
    private JTable jtable;
    private List<Stock> stocks;

    public StockOverviewDisplay(Display display) {
        this.display = display;
        this.label = new JLabel();

        StockMarket.getItemList().add(this);
        update(StockMarket.getStocks());

        if (stocks.size() > 0) setComponent();
        else label.setText("No Stocks available");

        this.add(label);
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
    }

    /**
     * This method will add all the table with the stock stats to the panel
     */
    private void setComponent() {
        String[] columnNames = {"Name", "Current Price", "Available", "+/-%"};
        String[][] data = stocks.stream()
                .map(stock -> new String[]{
                        stock.getName(),
                        String.valueOf(DefaultValues.DECIMAL_FORMAT.format((double) stock.getPriceHistory().get(stock.getPriceHistory().size() - 1))) ,
                        stock.getAvailable() + "",
                        getPlusMinus(10, stock)
                }).toArray(String[][]::new);

        jtable = new JTable(data, columnNames);

        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setShowVerticalLines(false);

        jtable.setFont(DefaultValues.FONT_MAIN);
        jtable.setBackground(DefaultValues.COLOR_BACKGROUND_LIGHT);

        jtable.getTableHeader().setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        jtable.getTableHeader().setFont(DefaultValues.FONT_BOLD);
        jtable.getTableHeader().setBorder(new EmptyBorder(0, 0, 0, 0));
        jtable.getTableHeader().setReorderingAllowed(false);

        jtable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (((String) table.getValueAt(row, 3)).contains("+"))
                    component.setForeground(DefaultValues.COLOR_TEXT_POSITIVE);
                else if (((String) table.getValueAt(row, 3)).contains("-"))
                    component.setForeground(DefaultValues.COLOR_TEXT_ERROR);
                else component.setForeground(DefaultValues.COLOR_TEXT_MAIN);
                return component;
            }
        });

        jtable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    display.setScreenIdentifier(StockMarket.getStockByName((String) target.getValueAt(target.getSelectedRow(), 0)).getTicker());
                    display.updateCurrentScreen(2);
                }
            }
        });

        JScrollPane pane = new JScrollPane(jtable);
        pane.getViewport().setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        pane.setBorder(new EmptyBorder(0, 0, 0, 0));

        this.add(pane);
    }

    /**
     * This method will check if the trend of the stock is positive or negative
     * @param lastPosition position to check
     * @param stock the current stock to check
     * @return the trend in percent
     */
    private String getPlusMinus(int lastPosition, Stock stock) {
        double result = Math.round(
                (stock.getPriceHistory().get(stock.getPriceHistory().size() - 1) /
                        (stock.getPriceHistory().size() - 1 > lastPosition ?
                                stock.getPriceHistory().get(stock.getPriceHistory().size() - 1 - lastPosition) : stock.getPriceHistory().get(0))
                        - 1) * 100) / 100.0;
        if (result > 0) return "+" + result;
        else return result + "";
    }

    /**
     * This method updates the stock list and reload the panel
     * @param stocks the new list of stocks
     */
    @Override
    public void update(List<Stock> stocks) {
        this.stocks = stocks;
        this.removeAll();
        setComponent();
        this.revalidate();
        this.repaint();
    }
}