package com.google;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Function;

import static java.awt.GridBagConstraints.*;

public class UserDisplay extends JPanel implements StockUpdateObserver {
    private final Display display;
    private JTable jtable;
    private final JLabel currentSharesLabel;
    private final JLabel historySharesLabel;

    public UserDisplay(Display display) {
        this.display = display;
        this.setBackground(DefaultValues.COLOR_BACKGROUND_MAIN);
        this.setLayout(new GridBagLayout());

        currentSharesLabel = new JLabel("Owned Shares", SwingConstants.CENTER);
        currentSharesLabel.setFont(DefaultValues.FONT_TITLE);

        historySharesLabel = new JLabel("Shares History", SwingConstants.CENTER);
        historySharesLabel.setFont(DefaultValues.FONT_TITLE);

        setUpUserDisplay();

        StockMarket.getItemList().add(this);
    }

    /**
     * This method will set the default values of the display
     */
    private void setUpUserDisplay() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = FIRST_LINE_START;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(currentSharesLabel, gridBagConstraints);

        gridBagConstraints.anchor = FIRST_LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.add(historySharesLabel, gridBagConstraints);

        gridBagConstraints.anchor = LINE_START;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        setCurrentTable(gridBagConstraints);

        gridBagConstraints.anchor = LINE_END;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        setHistoryTable(gridBagConstraints);
    }

    /**
     * This method will add the current stock table to the panel
     * @param gridBagConstraints the position of the table
     */
    private void setCurrentTable(GridBagConstraints gridBagConstraints) {

        String[] columnNames = {"Stock", "Amount", "Bought at", "Bought for", "Value", "Profit/Loss", "+/-%"};

        String[][] data = UserManager.currentUser.getPortfolio().getShares().stream().map(new Function<Share, String[]>() {
            @Override
            public String[] apply(Share share) {
                int amount = share.getAmount();
                double boughtFor = share.getBuyPrice() * amount;
                double currentValue = share.getStock().getCurrentPrice() * amount;
                return new String[]{share.getStock().getName(), amount + "", DefaultValues.DECIMAL_FORMAT.format(share.getBuyPrice()), DefaultValues.DECIMAL_FORMAT.format(boughtFor), DefaultValues.DECIMAL_FORMAT.format(currentValue), DefaultValues.DECIMAL_FORMAT.format(currentValue - boughtFor), getPlusMinus(boughtFor / amount, share.getStock()),};
            }
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
                if (((String) table.getValueAt(row, 6)).contains("+"))
                    component.setForeground(DefaultValues.COLOR_TEXT_POSITIVE);
                else if (((String) table.getValueAt(row, 6)).contains("-"))
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

        this.add(pane, gridBagConstraints);
    }

    /**
     * Sets the history history table to the panel
     * @param gridBagConstraints the position of the table
     */
    private void setHistoryTable(GridBagConstraints gridBagConstraints) {

        String[] columnNames = {"Stock", "Amount", "Bought at", "Bought for", "Value", "Profit/Loss", "+/-%"};

        String[][] data = UserManager.currentUser.getPortfolio().getSellHistory().stream().map(new Function<Share, String[]>() {
            @Override
            public String[] apply(Share share) {
                int amount = share.getAmount();
                double boughtFor = share.getBuyPrice() * amount;
                double currentValue = share.getStock().getCurrentPrice() * amount;
                return new String[]{share.getStock().getName(), amount + "", DefaultValues.DECIMAL_FORMAT.format(share.getBuyPrice()), DefaultValues.DECIMAL_FORMAT.format(boughtFor), DefaultValues.DECIMAL_FORMAT.format(currentValue), DefaultValues.DECIMAL_FORMAT.format(currentValue - boughtFor), getPlusMinus(boughtFor / amount, share.getStock()),};
            }
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
                if (((String) table.getValueAt(row, 6)).contains("+"))
                    component.setForeground(DefaultValues.COLOR_TEXT_POSITIVE);
                else if (((String) table.getValueAt(row, 6)).contains("-"))
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

        this.add(pane, gridBagConstraints);
    }

    /**
     * This method will check if the user has made profit or loss
     * @param boughtFor the price where the stock was bought
     * @param stock the current stock to check
     * @return the trend in percent
     */
    private String getPlusMinus(double boughtFor, Stock stock) {
        //calculate the profit/loss
        double currentValue = stock.getCurrentPrice();
        double profitLoss = currentValue - boughtFor;
        double percent = profitLoss / boughtFor * 100;

        if (percent > 0) return "+" + DefaultValues.DECIMAL_FORMAT.format(percent);
        else return DefaultValues.DECIMAL_FORMAT.format(percent);
    }

    /**
     * This method updates the stock list and reload the panel
     * @param stocks the new list of stocks
     */
    @Override
    public void update(List<Stock> stocks) {
        this.removeAll();

        setUpUserDisplay();

        revalidate();
        repaint();
    }
}
